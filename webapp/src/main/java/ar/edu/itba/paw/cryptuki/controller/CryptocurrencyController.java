package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.CryptocurrencyDto;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.exception.NoSuchCryptocurrencyException;
import ar.edu.itba.paw.model.Cryptocurrency;
import ar.edu.itba.paw.service.CryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("/api/cryptocurrencies")
@Component
public class CryptocurrencyController {

    private final CryptocurrencyService cryptocurrencyService;

    @Autowired
    public CryptocurrencyController(CryptocurrencyService cryptocurrencyService) {
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @GET
    @Produces("application/vnd.cryptuki.v1.cryptocurrency+json")
    public Response getCryptocurrencies() {

        Collection<CryptocurrencyDto> cryptocurrencies = cryptocurrencyService.getAllCryptocurrencies().stream().map(CryptocurrencyDto::fromCryptocurrency).collect(Collectors.toList());

        if (cryptocurrencies.isEmpty())
            return Response.noContent().build();
        final Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<Collection<CryptocurrencyDto>>(cryptocurrencies) {});
        ResponseHelper.setUnconditionalCache(responseBuilder);
        return responseBuilder.build();
    }

    @GET
    @Path("/{code}")
    @Produces("application/vnd.cryptuki.v1.cryptocurrency+json")
    public Response getCryptocurrency(@PathParam("code") String code) {
        Cryptocurrency cryptocurrency = cryptocurrencyService.getCryptocurrency(code).orElseThrow(()->new NoSuchCryptocurrencyException(code));
        final Response.ResponseBuilder responseBuilder = Response.ok(CryptocurrencyDto.fromCryptocurrency(cryptocurrency));
        ResponseHelper.setUnconditionalCache(responseBuilder);
        return responseBuilder.build();
    }
}
