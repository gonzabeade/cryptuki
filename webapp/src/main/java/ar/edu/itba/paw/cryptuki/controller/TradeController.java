package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.httpMethod.PATCH;
import ar.edu.itba.paw.cryptuki.annotation.validation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.dto.MessageDto;
import ar.edu.itba.paw.cryptuki.dto.TradeDto;
import ar.edu.itba.paw.cryptuki.dto.TradeRatingDTO;
import ar.edu.itba.paw.cryptuki.form.MessageForm;
import ar.edu.itba.paw.cryptuki.form.RatingForm;
import ar.edu.itba.paw.cryptuki.form.TradeStatusForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.cryptuki.utils.OfferBeanParam;
import ar.edu.itba.paw.cryptuki.utils.TradeBeanParam;
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
    @Produces("application/vnd.cryptuki.v1.trade-list+json")
    public Response listTrades(@Valid @BeanParam TradeBeanParam tradeBeanParam) {

        /**
            This method is for getting a list of trades.
            A query to get a list of trades only makes sense if:
                - We want to get all trades for a given offer.
                    - In that case, set from_offer=N
                OR
                - We want to get all trades for a given buyer
                    - In that case, set buyer=uname

                It does not make sense to ask for a list of offers in any other scenario
                This is why this method validates this and bifurcates
         */

        int page = tradeBeanParam.getPage();
        int pageSize = tradeBeanParam.getPageSize();
        List<String> status = tradeBeanParam.getStatus();
        Integer offer = tradeBeanParam.getOffer();
        String buyer = tradeBeanParam.getBuyer();

        // If status collection is empty, then no filtering is intended
        // Use every status in query
        Set<TradeStatus> statusSet = status.stream().map(TradeStatus::valueOf).collect(Collectors.toSet());
        if (statusSet.isEmpty())
            statusSet.addAll(Arrays.asList(TradeStatus.values()));

        Collection<Trade> trades;
        long tradeCount;

        if (buyer != null) {
            trades = tradeService.getTradesAsBuyer(buyer, page, pageSize, statusSet);
            tradeCount = tradeService.getTradesAsBuyerCount(buyer, statusSet);
        } else {
            Offer tradeOffer = offerService.getOfferById(offer).orElseThrow(()-> new NoSuchOfferException(offer));
            String username = tradeOffer.getSeller().getUsername().orElseThrow(InternalServerErrorException::new); // Legacy - From 1st Sprint, when users may not have usernames
            trades = tradeService.getTradesAsSeller(username, page, pageSize, statusSet, offer);
            tradeCount = tradeService.getTradesAsSellerCount(username, statusSet, offer);
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
    @Produces("application/vnd.cryptuki.v1.trade+json")
    public Response listTrade(@PathParam("tradeId") int tradeId) {
        Trade trade = getTrade(tradeId);
        return Response.ok(TradeDto.fromTrade(trade, uriInfo)).build();
    }

    @GET
    @Path("/{tradeId}/messages")
    @Produces("application/vnd.cryptuki.v1.message-list+json")
    public Response getTradeMessages(@PathParam("tradeId") int tradeId) {
        Trade trade = getTrade(tradeId);
        User seller = trade.getOffer().getSeller();
        User buyer = trade.getBuyer();
        List<MessageDto> messageDtos = trade.getMessageCollection().stream().map( m -> MessageDto.fromMessage(m, uriInfo, seller, buyer)).collect(Collectors.toList());

        String senderUname = SecurityContextHolder.getContext().getAuthentication().getName();
        if (senderUname.equals(trade.getBuyer().getUsername()))
            chatService.markBuyerMessagesAsSeen(trade.getTradeId());
        else
            chatService.markSellerMessagesAsSeen(trade.getTradeId());

        if (messageDtos.isEmpty())
            return Response.noContent().build();
        return Response.ok(new GenericEntity<Collection<MessageDto>>(messageDtos) {}).build();
    }

    @POST
    @Path("/{tradeId}/messages")
    @Consumes("application/vnd.cryptuki.v1.message+json")
    // @Produces not needed, because it returns no content. Valid according to RFC2616
    public Response postNewMessage(@NotNull @Valid MessageForm messageForm, @PathParam("tradeId") int tradeId) {

        String senderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(senderUsername).orElseThrow(()-> new NoSuchUserException(senderUsername));
        chatService.sendMessage(user.getId(), tradeId, messageForm.getMessage());
        return Response.noContent().build();
    }


    @PATCH
    @Path("/{tradeId}")
    @Consumes("application/vnd.cryptuki.v1.trade-status+json")
    // @Produces not needed, because it returns no content. Valid according to RFC5789
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

    @GET
    @Path("/{tradeId}/rating")
    @Produces("application/vnd.cryptuki.v1.rating+json")
    public Response getTradeRating(@PathParam("tradeId") int tradeId){
        Trade trade = tradeService.getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        return Response.ok(TradeRatingDTO.fromTrade(trade,uriInfo)).build();
    }

    @PATCH
    @Path("/{tradeId}/rating")
    @Consumes("application/vnd.cryptuki.v1.rating+json")
    // @Produces not needed, because it returns no content
    public Response rateTrade(@PathParam("tradeId") int tradeId, @NotNull @Valid RatingForm ratingForm){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        tradeService.rateCounterPartUserRegardingTrade(username,ratingForm.getRating(),tradeId);
        return Response.noContent().build();
    }


}
