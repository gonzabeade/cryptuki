package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.dto.MessageDto;
import ar.edu.itba.paw.cryptuki.dto.TradeDto;
import ar.edu.itba.paw.cryptuki.form.TradeForm;
import ar.edu.itba.paw.cryptuki.form.legacy.MessageForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.ChatService;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Path("api/offers/{offerId}/trades")
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

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!principal.equals(username))
            throw new ForbiddenException();

        Set<TradeStatus> statusSet = status.stream().map(TradeStatus::valueOf).collect(Collectors.toSet());
        if(statusSet.isEmpty())
            statusSet.addAll(Arrays.asList(TradeStatus.values()));

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
            tradeCount = trades.size();
        }
        else if(role.equals("seller")) {
            trades = tradeService
                    .getTradesAsSeller(username, page, pageSize, statusSet, offerId)
                    .stream().map(o -> TradeDto.fromTrade(o, uriInfo))
                    .collect(Collectors.toList());
            tradeCount = trades.size();
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
    public Response createTrade(@Valid TradeForm tradeForm, @PathParam("offerId") int offerId) {

        Optional<Offer> maybeOffer = offerService.getOfferById(offerId);
        if(!maybeOffer.isPresent())
            throw new NoSuchOfferException(offerId);

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userService.getUserByUsername(principal).get();

        //TODO: ver como se maneja el caso en el que quiero comprar mas o menos contidad de lo que esta permitido
        Trade trade = tradeService.makeTrade(offerId, buyer.getId(), tradeForm.getQuantity());

        if(trade == null)
            throw new BadRequestException();

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(trade.getTradeId()))
                .build();

        return Response.created(uri).build();
    }

    @PUT
    @Path("/{tradeId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateTrade(@PathParam("offerId") int offerId, @PathParam("tradeId") int tradeId) {
        return null;
    }

    @GET
    @Path("/{tradeId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTrade(@PathParam("offerId") int offerId, @PathParam("tradeId") int tradeId) {

        Optional<Trade> maybeTrade = tradeService.getTradeById(tradeId);
        if(!maybeTrade.isPresent())
            throw new NoSuchTradeException(tradeId);

        Trade trade = maybeTrade.get();
        if(offerId != trade.getOffer().getOfferId())
            throw new BadRequestException();

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!principal.equals(trade.getBuyer().getUsername().get()) && !principal.equals(trade.getOffer().getSeller().getUsername().get()))
            throw new ForbiddenException();

        return Response.ok(TradeDto.fromTrade(trade, uriInfo)).build();
    }

    @GET
    @Path("/{tradeId}/messages")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMessages(@PathParam("offerId") int offerId, @PathParam("tradeId") int tradeId) {

        Optional<Trade> maybeTrade = tradeService.getTradeById(tradeId);
        if(!maybeTrade.isPresent())
            throw new NoSuchTradeException(tradeId);

        Trade trade = maybeTrade.get();
        if(offerId != trade.getOffer().getOfferId())
            throw new BadRequestException();

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!principal.equals(trade.getBuyer().getUsername().get()) && !principal.equals(trade.getOffer().getSeller().getUsername().get()))
            throw new ForbiddenException();

        Collection<MessageDto> messageDtos = trade.getMessageCollection()
                .stream().map(o -> MessageDto.fromMessage(o, uriInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<Collection<MessageDto>>(messageDtos) {}).build();
    }

    @POST
    @Path("/{tradeId}/messages")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createMessage(@Valid MessageForm messageForm, @PathParam("offerId") int offerId, @PathParam("tradeId") int tradeId){

        Optional<Trade> maybeTrade = tradeService.getTradeById(tradeId);
        if(!maybeTrade.isPresent())
            throw new NoSuchTradeException(tradeId);

        Trade trade = maybeTrade.get();
        if(offerId != trade.getOffer().getOfferId())
            throw new BadRequestException();

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!principal.equals(trade.getBuyer().getUsername().get()) && !principal.equals(trade.getOffer().getSeller().getUsername().get()))
            throw new ForbiddenException();

        User sender = userService.getUserByUsername(principal).get();

        chatService.sendMessage(sender.getId(), tradeId, messageForm.getMessage());

        return Response.status(Response.Status.CREATED).build();
    }

}
