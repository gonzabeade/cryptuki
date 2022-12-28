package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.ValueOfEnum;
import ar.edu.itba.paw.cryptuki.dto.ComplainDto;
import ar.edu.itba.paw.cryptuki.form.legacy.admin.SolveComplainForm;
import ar.edu.itba.paw.cryptuki.form.legacy.support.TradeComplainSupportForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.cryptuki.utils.ComplainBeanParam;
import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainFilter;
import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.service.ComplainService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("api/complaints")
@Component
public class ComplainsController {

    private final ComplainService complainService;
    private final UserService userService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public ComplainsController(ComplainService complainService, UserService userService) {
        this.complainService = complainService;
        this.userService = userService;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComplaint(@Valid TradeComplainSupportForm tradeComplainSupportForm) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Complain complain = complainService.makeComplain(tradeComplainSupportForm.toComplainPO(username));

        final URI uri = uriInfo.getBaseUriBuilder()
                .replacePath("/api/complains")
                .path(String.valueOf(complain.getComplainId()))
                .build();

        return Response.created(uri).build();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaints(@BeanParam ComplainBeanParam complainBeanParam) {
        if(complainBeanParam == null)
            return Response.noContent().build();

        ComplainFilter complainFilter = complainBeanParam.mapToComplainFilter();
        Collection<ComplainDto> complains = complainService.getComplainsBy(complainFilter)
                .stream().map(o -> ComplainDto.fromComplain(o, uriInfo))
                .collect(Collectors.toList());
        long offerCount = complainService.countComplainsBy(complainFilter);

        if (complains.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<ComplainDto>>(complains) {
        });
        return ResponseHelper.genLinks(rb, uriInfo, complainBeanParam.getPage(), complainBeanParam.getPageSize(), offerCount).build();
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
            @Valid SolveComplainForm solveComplainForm,
            @PathParam("id") int id) {
        String Username = SecurityContextHolder.getContext().getAuthentication().getName();
        Complain complain = complainService.getComplainById(id).orElseThrow(() -> new NoSuchComplainException(id));

        if (solveComplainForm.getResult().equals("dismiss")) {
            complainService.closeComplainWithDismiss(id, Username, solveComplainForm.getComments());
            return Response.status(Response.Status.CREATED).build();
        } else if (solveComplainForm.getResult().equals("kick")) {
            complainService.closeComplainWithKickout(id, Username, solveComplainForm.getComments(), complain.getComplainer().getId());
            return Response.status(Response.Status.CREATED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    @GET
    @Path("/{id}/resolution")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaintResolution(@PathParam("id") int id) {
        Complain complain = complainService.getComplainById(id).
                orElseThrow(() -> new NoSuchComplainException(id));
        if(complain.getStatus().equals(ComplainStatus.PENDING))
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(ComplainDto.fromComplain(complain, uriInfo)).build();
    }

}