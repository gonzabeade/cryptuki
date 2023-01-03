package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.httpMethod.PATCH;
import ar.edu.itba.paw.cryptuki.dto.OfferDto;
import ar.edu.itba.paw.cryptuki.form.TradeForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.cryptuki.form.legacy.ModifyOfferForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.cryptuki.utils.OfferBeanParam;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.OfferFilter;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;


@Path("/api/offers")
@Component
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;
    private final TradeService tradeService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public OfferController(OfferService offerService, UserService userService, TradeService tradeService) {
        this.offerService = offerService;
        this.userService = userService;
        this.tradeService = tradeService;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response listOffers(@BeanParam OfferBeanParam offerBeanParam) {

        OfferFilter filter = offerBeanParam.toOfferFilter();

        Collection<OfferDto> offers = offerService.getOffers(filter).stream().map(o -> OfferDto.fromOffer(o, uriInfo)).collect(Collectors.toList());
        long offerCount = offerService.countOffers(filter);

        if (offers.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<OfferDto>>(offers) {
        });
        return ResponseHelper.genLinks(rb, uriInfo, offerBeanParam.getPage(), offerBeanParam.getPageSize(), offerCount).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOffer(@PathParam("id") int id) {
        Offer offer = offerService.getOfferById(id).orElseThrow(() -> new NoSuchOfferException(id));
        return Response.ok(OfferDto.fromOffer(offer, uriInfo)).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createOffer(@Valid UploadOfferForm offerForm) {

        String who = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(who).orElseThrow(() -> new NoSuchUserException(who));
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
    public Response modifyOffer(@Valid ModifyOfferForm offerForm, @PathParam("id") int id) {

        String who = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(who).orElseThrow(() -> new NoSuchUserException(who));
        offerForm.setSellerId(user.getId());
        try {
            offerService.modifyOffer(offerForm.toOfferParameterObject().withOfferId(id));
        } catch (RuntimeException e) {
            //TODO: 403 may not apply.
            return Response.status(Response.Status.fromStatusCode(403)).build();
        }

        return Response.ok().build();
    }


    @GET
    @Path("/{offerId}/trades")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTrades(@Valid TradeForm tradeForm, @PathParam("offerId") int offerId) {

        final URI uri = uriInfo.getBaseUriBuilder()
                .path("/api/trades")
                .queryParam("from_offer", offerId)
                .build();

        return Response.status(Response.Status.MOVED_PERMANENTLY).location(uri).build();
    }

    @POST
    @Path("/{offerId}/trades")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createTrade(@Valid TradeForm tradeForm, @PathParam("offerId") int offerId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userService.getUserByUsername(username).orElseThrow(() -> new NoSuchUserException(username)); // Will never throw exception

        Trade trade = tradeService.makeTrade(offerId, buyer.getId(), tradeForm.getQuantity());

        final URI uri = uriInfo.getBaseUriBuilder()
                .path("/api/trades")
                .path(String.valueOf(trade.getTradeId()))
                .build();

        return Response.created(uri).build();
    }
}
