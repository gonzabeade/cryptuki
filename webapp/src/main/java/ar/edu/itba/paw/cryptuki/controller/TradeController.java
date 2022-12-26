package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.dto.MessageDto;
import ar.edu.itba.paw.cryptuki.dto.OfferDto;
import ar.edu.itba.paw.cryptuki.dto.TradeDto;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.ChatService;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.apache.taglibs.standard.lang.jstl.ArraySuffix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.stream.Collectors;

@Path("/api/trades")
@Component
public class TradeController {

    private final TradeService tradeService;
    private final ChatService chatService;
    private final UserService userService;
    private final OfferService offerService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public TradeController(TradeService tradeService, ChatService chatService, UserService userService, OfferService offerService) {
        this.tradeService = tradeService;
        this.chatService = chatService;
        this.userService = userService;
        this.offerService = offerService;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response listTrades(
            @QueryParam("page") @DefaultValue("0") final int page,
            @QueryParam("per_page") @DefaultValue("5") final int pageSize,
            @QueryParam("status") @CollectionOfEnum(enumClass = TradeStatus.class) final List<String> status,
            @QueryParam("from_offer") final Integer offerId,
            @QueryParam("buyer") final String buyerUsername
    ) {

        // Do not allow queries on these QueryParams simultaneously
        boolean isBuyerPresent = buyerUsername != null;
        boolean isOfferIdPresent = offerId != null;

        if ((isBuyerPresent && isOfferIdPresent) || !(isBuyerPresent || isOfferIdPresent))
            throw new IllegalArgumentException("Exactly one of the following filters is allowed: `from_offer`, `buyer`");

        // If status collection is empty, then no filtering is intended
        // Use every status in query
        Set<TradeStatus> statusSet = status.stream().map(o->TradeStatus.valueOf(o)).collect(Collectors.toSet());
        if (statusSet.isEmpty())
            statusSet.addAll(Arrays.asList(TradeStatus.values()));

        Collection<Trade> trades;
        long tradeCount;
        if (isBuyerPresent) {
            trades = tradeService.getTradesAsBuyer(buyerUsername, page, pageSize, statusSet);
            tradeCount = tradeService.getTradesAsBuyerCount(buyerUsername, statusSet);
        } else {
            Offer offer = offerService.getOfferById(offerId).orElseThrow(()-> new NoSuchOfferException(offerId));
            String username = offer.getSeller().getUsername().get(); // Legacy - From 1st Sprint, when users may not have usernames

            // If access is denied, hide 403 response status under a 404,
            // to prevent making information public
            try {
                trades = tradeService.getTradesAsSeller(username, page, pageSize, statusSet, offerId);
                tradeCount = tradeService.getTradesAsSellerCount(username, statusSet, offerId);
            } catch (AccessDeniedException ade) {
                throw new NoSuchOfferException(offerId, ade);
            }

        }

        if (trades.isEmpty())
            return Response.noContent().build();

        Collection<TradeDto> tradesDto = trades.stream().map(t -> TradeDto.fromTrade(t, uriInfo)).collect(Collectors.toList());
        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<TradeDto>>(tradesDto) {});
        return ResponseHelper.genLinks(rb, uriInfo, page, pageSize, tradeCount).build();
    }

    private Trade getTrade(int tradeId) {
        Trade trade;
        try {
            trade = tradeService.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        } catch (AccessDeniedException ade) {
            // Hide 403 Forbidden into a 404 for security concerns
            throw new NoSuchTradeException(tradeId, ade);
        }
        return trade;
    }

    @GET
    @Path("/{tradeId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listTrade(@PathParam("tradeId") int tradeId) {
        Trade trade = getTrade(tradeId);
        return Response.ok(TradeDto.fromTrade(trade, uriInfo)).build();
    }

    @GET
    @Path("/{tradeId}/messages")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTradeMessages(@PathParam("tradeId") int tradeId) {
        Trade trade = getTrade(tradeId);
        User seller = trade.getOffer().getSeller();
        User buyer = trade.getBuyer();
        List<MessageDto> messageDtos = trade.getMessageCollection().stream().map( m -> MessageDto.fromMessage(m, uriInfo, seller, buyer)).collect(Collectors.toList());

        if (messageDtos.isEmpty())
            return Response.noContent().build();
        return Response.ok(new GenericEntity<Collection<MessageDto>>(messageDtos) {}).build();
    }
}
