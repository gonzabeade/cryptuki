package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.CryptocurrencyDto;
import ar.edu.itba.paw.exception.NoSuchCryptocurrencyException;
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
import java.util.Optional;
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
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCryptocurrencies() {

        Collection<CryptocurrencyDto> cryptocurrencies = cryptocurrencyService.getAllCryptocurrencies().stream().map( c->CryptocurrencyDto.fromCryptocurrency(c)).collect(Collectors.toList());

        if (cryptocurrencies.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<Collection<CryptocurrencyDto>>(cryptocurrencies) {}).build();
    }

    @GET
    @Path("/{code}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCryptocurrency(@PathParam("code") String code) {

        Optional<CryptocurrencyDto> maybeCryptocurrency = cryptocurrencyService.getCryptocurrency(code).map(CryptocurrencyDto::fromCryptocurrency);

        if (!maybeCryptocurrency.isPresent()) {
            throw new NoSuchCryptocurrencyException(code);
        }

        return Response.ok(maybeCryptocurrency.get()).build();
    }
}
