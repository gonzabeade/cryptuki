package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.httpMethod.PATCH;
import ar.edu.itba.paw.cryptuki.annotation.validation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.dto.MessageDto;
import ar.edu.itba.paw.cryptuki.dto.TradeDto;
import ar.edu.itba.paw.cryptuki.form.MessageForm;
import ar.edu.itba.paw.cryptuki.form.TradeStatusForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.ChatService;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
            throw new IllegalArgumentException("Exactly one of the following filters must be provided: `from_offer`, `buyer`");

        // If status collection is empty, then no filtering is intended
        // Use every status in query
        Set<TradeStatus> statusSet = status.stream().map(TradeStatus::valueOf).collect(Collectors.toSet());
        if (statusSet.isEmpty())
            statusSet.addAll(Arrays.asList(TradeStatus.values()));

        Collection<Trade> trades;
        long tradeCount;
        if (isBuyerPresent) {
            trades = tradeService.getTradesAsBuyer(buyerUsername, page, pageSize, statusSet);
            tradeCount = tradeService.getTradesAsBuyerCount(buyerUsername, statusSet);
        } else {
            Offer offer = offerService.getOfferById(offerId).orElseThrow(()-> new NoSuchOfferException(offerId));
            String username = offer.getSeller().getUsername().orElseThrow(InternalServerErrorException::new); // Legacy - From 1st Sprint, when users may not have usernames

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
        ResponseHelper.genLinks(rb, uriInfo, page, pageSize, tradeCount);
        return rb.build();
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

    @POST
    @Path("/{tradeId}/messages")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postNewMessage(@NotNull @Valid MessageForm messageForm, @PathParam("tradeId") int tradeId) {

        String senderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(senderUsername).orElseThrow(()-> new NoSuchUserException(senderUsername));
        chatService.sendMessage(user.getId(), tradeId, messageForm.getMessage());
        return Response.noContent().build();
    }


    @PATCH
    @Path("/{tradeId}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response modifyTrade(@PathParam("tradeId") int tradeId, @NotNull @Valid TradeStatusForm tradeStatusForm) {
        switch (TradeStatus.valueOf(tradeStatusForm.getNewStatus())){
            case ACCEPTED:
                this.tradeService.acceptTrade(tradeId);break;
            case SOLD:
                this.tradeService.sellTrade(tradeId);break;
            case DELETED:
                this.tradeService.deleteTrade(tradeId);break;
            case REJECTED:
                this.tradeService.rejectTrade(tradeId);break;
            case PENDING: throw new BadRequestException("Trade can not be set to PENDING");
        }
        return Response.noContent().build();
    }


}
