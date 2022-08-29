package ar.edu.itba.paw.cryptuki.mapper;


import ar.edu.itba.paw.cryptuki.dto.NotFoundErrorDto;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final String MESSAGE = "Resource Not Found";

    @Override
    public Response toResponse(NotFoundException e) {
        NotFoundErrorDto dto = NotFoundErrorDto.fromMessage(MESSAGE);
        return Response.status(Response.Status.NOT_FOUND).entity(dto).build();
    }
}
