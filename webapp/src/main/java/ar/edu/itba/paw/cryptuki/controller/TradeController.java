package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.dto.OfferDto;
import ar.edu.itba.paw.cryptuki.dto.TradeDto;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.ChatService;
import ar.edu.itba.paw.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("api/offers/{offerId}/trades")
@Component
public class TradeController {

    private final TradeService tradeService;
    private final ChatService chatService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public TradeController(TradeService tradeService, ChatService chatService) {
        this.tradeService = tradeService;
        this.chatService = chatService;
    }

    @GET
    @Path("/user/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTrades(
            @PathParam("offerId") int offerId,
            @PathParam("username") String username,
            @NotNull @QueryParam("role") String role,
            @QueryParam("page") @DefaultValue("0") final int page,
            @QueryParam("per_page") @DefaultValue("5") final int pageSize,
            @NotNull @QueryParam("status") @CollectionOfEnum(enumClass = TradeStatus.class) final List<String> status
    ) {

        Set<TradeStatus> statusSet = status.stream().map(TradeStatus::valueOf).collect(Collectors.toSet());
        long tradeCount;
        Collection<TradeDto> trades;

        if(role.equals("buyer")) {
            trades = tradeService
                    .getTradesAsBuyer(username, page, pageSize, statusSet)
                    .stream().map(o -> TradeDto.fromTrade(o, uriInfo))
                    //TODO: estoy sacando solo las trades que van con esta oferta
                    //TODO: para mandar diferentes cosas por buyer y por seller habria que hacer algunas cosas radicales en los paths
                    .filter(tradeDto -> tradeDto.getOfferId() == offerId)
                    .collect(Collectors.toList());
            tradeCount = tradeService.getTradesAsBuyerCount(username, statusSet);
        }
        else if(role.equals("seller")) {
            trades = tradeService
                    .getTradesAsSeller(username, page, pageSize, statusSet, offerId)
                    .stream().map(o -> TradeDto.fromTrade(o, uriInfo))
                    .collect(Collectors.toList());
            tradeCount = tradeService.getTradesAsBuyerCount(username, statusSet);
        }
        else
            throw new BadRequestException();

        if(tradeCount == 0)
            return Response.noContent().build();

        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<TradeDto>>(trades) {});
        return ResponseHelper.genLinks(rb, uriInfo, page, pageSize, tradeCount).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createTrade(@PathParam("offerId") int offerId) {
        return null;
    }

    @PUT
    @Path("/{tradeId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response cancelTrade(@PathParam("offerId") int offerId, @PathParam("tradeId") int tradeId) {
        return null;
    }

    @GET
    @Path("/{tradeId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTrade(@PathParam("tradeId") int tradeId) {
        return null;
    }

    @GET
    @Path("/{id}/messages")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMessages(@PathParam("id") int id) {
        return null;
    }

    @POST
    @Path("/{id}/messages")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createMessage(@PathParam("id") int id){
        return null;
    }

}
