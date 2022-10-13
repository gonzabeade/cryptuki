package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.annotation.ValueOfEnum;
import ar.edu.itba.paw.cryptuki.dto.OfferDto;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Solve <4>The root of the app was not properly defined. Either use a Servlet 3.x container or add an init-param jersey.config.servlet.filter.contextPath to the filter configuration. Due to Servlet 2.x API, Jersey cannot determine the request base URI solely from the ServletContext. The application will most likely not work.
// TODO: Why API Response is ordered alphabetically?

@Path("/api/offers")
@Component
public class OfferController {

    public final OfferService offerService;
    public final UserService userService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public OfferController(OfferService offerService, UserService userService) {
        this.offerService = offerService;
        this.userService = userService;
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response listOffers(
            @QueryParam("page") @DefaultValue("0") final int page,
            @QueryParam("per_page") @DefaultValue("1") final int pageSize,
            @QueryParam("crypto_code") final List<String> cryptoCodes,
            @QueryParam("location") @CollectionOfEnum(enumClass = Location.class) final List<String> locations,
            @QueryParam("status") @CollectionOfEnum(enumClass = OfferStatus.class) final List<String> status,
            @QueryParam("exclude_user") final List<String> excludedUsernames,
            @QueryParam("by_user") final List<String> restrictedToUsernames
    ) {

        OfferFilter filter = new OfferFilter()
                .excludeUsernames(excludedUsernames)
                .restrictedToUsernames(restrictedToUsernames)
                .withCryptoCodes(cryptoCodes)
                .withLocations(locations)
                .withOfferStatus(status.stream().map(o->OfferStatus.valueOf(o)).collect(Collectors.toList()))
                .withPage(page)
                .withPageSize(pageSize);

        Collection<OfferDto> offers = offerService.getOffers(filter).stream().map(o -> OfferDto.fromOffer(o, uriInfo)).collect(Collectors.toList());
        long offerCount = offerService.countOffers(filter);

        if (offers.isEmpty())
            return Response.noContent().build();
        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<OfferDto>>(offers) {});
        return ResponseHelper.genLinks(rb, uriInfo, page, pageSize, offerCount).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOffer(@PathParam("id") int id) {
        Optional<OfferDto> maybeOffer = offerService.getOfferById(id).map(o -> OfferDto.fromOffer(o, uriInfo));

        if (!maybeOffer.isPresent())
            throw new NoSuchOfferException(id);
        
        return Response.ok(maybeOffer.get()).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createOffer(@Valid UploadOfferForm offerForm) {

        String who = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(who).orElseThrow(()->new NoSuchUserException(who));
        offerForm.setSellerId(user.getId());
        Offer offer = offerService.makeOffer(offerForm.toOfferParameterObject());

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(offer.getOfferId()))
                .build();

        return Response.created(uri).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response modifyOffer(@Valid UploadOfferForm offerForm, @PathParam("id") int id) {

        String who = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(who).orElseThrow(()->new NoSuchUserException(who));
        offerForm.setSellerId(user.getId());

        Offer offer = offerService.modifyOffer(offerForm.toOfferParameterObject().withOfferId(id));

        return Response.ok().build();
    }
}
