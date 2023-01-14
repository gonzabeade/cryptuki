package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.ComplainDto;
import ar.edu.itba.paw.cryptuki.dto.ComplainResolutionDto;
import ar.edu.itba.paw.cryptuki.form.legacy.admin.SolveComplainForm;
import ar.edu.itba.paw.cryptuki.form.legacy.support.TradeComplainSupportForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.cryptuki.utils.ComplainBeanParam;
import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainFilter;
import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("api/complaints")
@Component
public class ComplainController {

    private final ComplainService complainService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public ComplainController(ComplainService complainService) {
        this.complainService = complainService;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComplaint(@NotNull @Valid TradeComplainSupportForm tradeComplainSupportForm) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Complain complain = complainService.makeComplain(tradeComplainSupportForm.toComplainPO(username));

        final URI uri = uriInfo.getBaseUriBuilder()
                .replacePath("/api/complaints")
                .path(String.valueOf(complain.getComplainId()))
                .build();

        return Response.created(uri).build();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaints(@BeanParam ComplainBeanParam complainBeanParam) {
        ComplainFilter complainFilter = complainBeanParam.toComplainFilter();
        Collection<ComplainDto> complains = complainService.getComplainsBy(complainFilter)
                .stream()
                .map(o -> ComplainDto.fromComplain(o, uriInfo))
                .collect(Collectors.toList());
        long offerCount = complainService.countComplainsBy(complainFilter);

        if (complains.isEmpty())
            return Response.noContent().build();
        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<ComplainDto>>(complains) {});
        ResponseHelper.genLinks(rb, uriInfo, complainBeanParam.getPage(), complainBeanParam.getPageSize(), offerCount);
        return rb.build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaint(@PathParam("id") int id) {
        Complain complain = complainService.getComplainById(id).orElseThrow(() -> new NoSuchComplainException(id));
        return Response.ok(ComplainDto.fromComplain(complain, uriInfo)).build();
    }

    @POST
    @Path("/{id}/resolution")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComplaintResolution(
            @NotNull @Valid SolveComplainForm solveComplainForm,
            @PathParam("id") int id
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Complain complain = complainService.getComplainById(id).orElseThrow(() -> new NoSuchComplainException(id));
        complainService.closeComplain(solveComplainForm.toSolveComplainPO(username,id));

        final URI uri = uriInfo.getBaseUriBuilder()
                .replacePath("/api/complaints")
                .path(String.valueOf(complain.getComplainId()))
                .path("resolution")
                .build();

        return Response.created(uri).build();
    }


    @GET
    @Path("/{id}/resolution")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaintResolution(@PathParam("id") int id) {
        Complain complain = complainService.getComplainById(id).
                orElseThrow(() -> new NoSuchComplainException(id));

        if(complain.getStatus().equals(ComplainStatus.PENDING))
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(ComplainResolutionDto.fromComplain(complain, uriInfo)).build();
    }

}