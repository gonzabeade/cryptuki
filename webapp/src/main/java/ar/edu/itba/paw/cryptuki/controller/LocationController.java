package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.CryptocurrencyDto;
import ar.edu.itba.paw.cryptuki.dto.LocationDto;
import ar.edu.itba.paw.model.LocationCountWrapper;
import ar.edu.itba.paw.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("/api/locations")
@Component
public class LocationController {

    private final OfferService offerService;

    @Autowired
    public LocationController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLocations() {
        Collection<LocationDto> collection = offerService.getOfferCountByLocation().stream().map(LocationDto::fromLocationCountWrapper).collect(Collectors.toList());

        if (collection.isEmpty())
            return Response.noContent().build();

        return Response.ok(new GenericEntity<Collection<LocationDto>>(collection) {}).build();
    }



}
