package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.ValueOfEnum;
import ar.edu.itba.paw.cryptuki.dto.ComplainDto;
import ar.edu.itba.paw.cryptuki.form.legacy.admin.SolveComplainForm;
import ar.edu.itba.paw.cryptuki.form.legacy.support.TradeComplainSupportForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
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

@Path("api/complains")
@Component
public class ComplainsController {

    private final ComplainService complainService;
    private final UserService userService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public ComplainsController(ComplainService complainService, UserService userService){
        this.complainService = complainService;
        this.userService = userService;
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

    //TODO: ver de usar la annotation @BeanParam para trabajar con objetos en vez de con parametros
    //https://stackoverflow.com/questions/57275310/queryparam-incorrect-parameter-type-on-a-java-class
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getComplaints(
            @QueryParam("page") @DefaultValue("0") final int page,
            @QueryParam("per_page") @DefaultValue("5") final int pageSize,
            @QueryParam("complain_status") @ValueOfEnum(enumClass = ComplainStatus.class) final String complainStatus,
            @QueryParam("offer_id") final int offerId,
            @QueryParam("trade_id") final int tradeId,
            @QueryParam("complainer_username") final List<String> complainerUsernames,
            @QueryParam("moderator_username") final List<String> moderatorUsernames,
            @QueryParam("from_date") final String fromDate,
            @QueryParam("to_date") final String toDate
        ) {

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userService.getUserByUsername(principal).get().getUserAuth().getRole().equals(Role.ROLE_ADMIN))
            throw new ForbiddenException();

        //TODO: ver con gonza como manejar el caso en el que no se pasan query params
        //TODO: el filtro de offers toma arreglos en vez de complains
        //TODO: para poder tomar arreglos hay que hacer cambios en los atributos del filtro
        ComplainFilter complainFilter = new ComplainFilter()
                .withPage(page)
                .withPageSize(pageSize)
                .restrictedToComplainerUsernames(complainerUsernames)
                .restrictedToModeratorUsernames(moderatorUsernames);
//                .withComplainStatus(ComplainStatus.valueOf(complainStatus))
//                .restrictedToOfferId(offerId)
//                .restrictedToTradeId(tradeId)
//                .from(LocalDate.parse(fromDate))
//                .to(LocalDate.parse(toDate));

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
        Optional<ComplainDto> maybeOffer = complainService.getComplainById(id).map(o -> ComplainDto.fromComplain(o, uriInfo));

        //TODO: ver como pasar la excepcion a status code con gonza
        if (!maybeOffer.isPresent())
            throw new NoSuchComplainException(id);

        return Response.ok(maybeOffer.get()).build();
    }

    @POST
    @Path("/{id}/resolution")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComplaintResolution(
            @Valid SolveComplainForm solveComplainForm,
            @PathParam("id") int id) {

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userService.getUserByUsername(principal).get().getUserAuth().getRole().equals(Role.ROLE_ADMIN))
            throw new ForbiddenException();

        Optional<Complain> maybeComplain = complainService.getComplainById(id);
        if(!maybeComplain.isPresent())
            throw new NoSuchComplainException(id);

        Complain complain = maybeComplain.get();

        //TODO: el uso de estos metodos elimina las complains
        //TODO: para mi habria que modificar el estado de las complains y no eliminar
        if(solveComplainForm.getResult().equals("dismiss")) {
            complainService.closeComplainWithDismiss(id, principal, solveComplainForm.getComments());
            return Response.status(Response.Status.CREATED).build();
        }
        else if(solveComplainForm.getResult().equals("kick")){
            complainService.closeComplainWithKickout(id, principal, solveComplainForm.getComments(), complain.getComplainer().getId());
            return Response.status(Response.Status.CREATED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
