package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.ComplainDto;
import ar.edu.itba.paw.cryptuki.form.legacy.admin.SolveComplainForm;
import ar.edu.itba.paw.cryptuki.form.legacy.support.TradeComplainSupportForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainFilter;
import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("api/complains")
@Component
public class ComplainsController {

    private final ComplainService complainService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public ComplainsController(ComplainService complainService){
        this.complainService = complainService;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComplaint(@Valid TradeComplainSupportForm tradeComplainSupportForm) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals(tradeComplainSupportForm.getUsername()))
            throw new ForbiddenException();

        Complain complain = complainService.makeComplain(tradeComplainSupportForm.toComplainPO(tradeComplainSupportForm.getUsername()));

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(complain.getComplainId()))
                .build();

        return Response.created(uri).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaints(
            @QueryParam("page") @DefaultValue("0") final int page,
            @QueryParam("per_page") @DefaultValue("5") final int pageSize,
            @QueryParam("complain_status") final String complainStatus, //TODO: usar annotation para validar que sea un enum
            @QueryParam("offer_id") final int offerId,
            @QueryParam("trade_id") final int tradeId,
            @QueryParam("complainer_username") final String complainerUsername,
            @QueryParam("moderator_username") final String moderatorUsername
        ) {

        //TODO: ver como obtener LocalDate como QueryParam, mirar si hay alguna annotation
        ComplainFilter complainFilter = new ComplainFilter()
                .withPage(page)
                .withPageSize(pageSize)
                .withComplainStatus(ComplainStatus.valueOf(complainStatus))
                .restrictedToOfferId(offerId)
                .restrictedToTradeId(tradeId)
                .restrictedToComplainerUsername(complainerUsername)
                .restrictedToModeratorUsername(moderatorUsername);

        Collection<ComplainDto> complains = complainService.getComplainsBy(complainFilter).stream().map(o -> ComplainDto.fromComplain(o, uriInfo)).collect(Collectors.toList());
        long offerCount = complainService.countComplainsBy(complainFilter);

        if (complains.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<ComplainDto>>(complains) {});
        return ResponseHelper.genLinks(rb, uriInfo, page, pageSize, offerCount).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaint(@PathParam("id") int id) {

        return null;
    }


    @GET
    @Path("/{id}/resolution")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaintResolution(@PathParam("id") int id) {

        return null;
    }

    @POST
    @Path("/{id}/resolution")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComplaintResolution(@Valid SolveComplainForm solveComplainForm, @PathParam("id") int id) {

        return null;
    }
}
