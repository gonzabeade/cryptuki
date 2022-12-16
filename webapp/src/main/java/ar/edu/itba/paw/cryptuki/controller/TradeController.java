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

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("api/trades")
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
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTrades(
            @QueryParam("page") @DefaultValue("0") final int page,
            @QueryParam("per_page") @DefaultValue("5") final int pageSize,
            @QueryParam("crypto_code") final List<String> cryptoCodes,
            @QueryParam("location") @CollectionOfEnum(enumClass = Location.class) final List<String> locations,
            @QueryParam("status") @CollectionOfEnum(enumClass = TradeStatus.class) final List<String> status,
            @QueryParam("by_user") final List<String> restrictedToUsernames
    ) {

        TradeFilter filter = new TradeFilter()
                .restrictedToUsernames(restrictedToUsernames)
                .withCryptoCodes(cryptoCodes)
                .withLocations(locations)
                .withTradeStatus(status.stream().map(o-> TradeStatus.valueOf(o)).collect(Collectors.toList()))
                .withPage(page)
                .withPageSize(pageSize);

//        Collection<TradeDto> trades = tradeService.getTrades(filter).stream().map(o -> TradeDto.fromTrade(o, uriInfo)).collect(Collectors.toList());
//        long tradeCount = tradeService.countTrades(filter);
//
//        if (trades.isEmpty())
//            return Response.noContent().build();
//
//        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<TradeDto>>(trades) {});
//        return ResponseHelper.genLinks(rb, uriInfo, page, pageSize, tradeCount).build();
        return null;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createTrade() {
        return null;
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response cancelTrade(@PathParam("id") int id) {
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTrade(@PathParam("id") int id) {
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
