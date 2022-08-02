package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.OfferDto;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.OfferFilter;
import ar.edu.itba.paw.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Solve <4>The root of the app was not properly defined. Either use a Servlet 3.x container or add an init-param jersey.config.servlet.filter.contextPath to the filter configuration. Due to Servlet 2.x API, Jersey cannot determine the request base URI solely from the ServletContext. The application will most likely not work.


// TODO: Why API Response is ordered alphabetically?
@Path("offers")
@Component
public class OfferController {

    public final OfferService offerService;

    @Context
    public UriInfo uriInfo; // has information on current request. By default, points to the request that was just made.

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    // GET /offers
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response listOffers(@QueryParam("page") @DefaultValue("0") final int page, @QueryParam("pageSize") @DefaultValue("1") final int pageSize) {

        OfferFilter filter = new OfferFilter()
                .withPage(page)
                .withPageSize(pageSize);

        Collection<OfferDto> offers = offerService.getBuyableOffers(filter).stream().map(o -> OfferDto.fromOffer(o, uriInfo)).collect(Collectors.toList());

        if (offers.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<Collection<OfferDto>>(offers) {})
                // TODO: make these better
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", page-1).build(), "prev")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", page+1).build(), "next")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 0).build(), "first")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 999).build(), "last")
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getOffer(@PathParam("id") int id) {
        Optional<OfferDto> maybeOffer = offerService.getOfferById(id).map(o -> OfferDto.fromOffer(o, uriInfo));

        if (!maybeOffer.isPresent())
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(maybeOffer.get()).build();
    }


    @POST
    public Response createOfferSample(String offerSample) {
        System.out.println("OFFER CREATED: "+offerSample);

        OfferFilter filter = new OfferFilter().withPage(0);
        OfferDto dto = offerService.getOfferById(23).map(o -> OfferDto.fromOffer(o, uriInfo)).get(); // TODO: when I create, It wont return optiona

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(dto.getOfferId()))
                .build();

        return Response.created(uri).build();
    }
}
