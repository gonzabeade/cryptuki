package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.CryptocurrencyDto;
import ar.edu.itba.paw.cryptuki.dto.LocationDto;
import ar.edu.itba.paw.model.Location;
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
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
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
    // TODO: Check if it can be done better from the persistence layer. The implementation uses the methods defined in TP2
    public Response getLocations() {
        Collection<LocationCountWrapper> collection = offerService.getOfferCountByLocation(); // .stream().map(LocationDto::fromLocationCountWrapper).collect(Collectors.toList());

        Map<Location, Long> extendedResult = new EnumMap<>(Location.class);
        for (LocationCountWrapper l: collection)
            extendedResult.put(l.getLocation(), l.getLocationCount());

        for (Location l: Location.values())
            extendedResult.putIfAbsent(l, 0l);

        Collection<LocationDto> locationDtos = extendedResult.entrySet().stream().map(LocationDto::fromMapEntry).collect(Collectors.toList());

        if (locationDtos.isEmpty())
            return Response.noContent().build();
        return Response.ok(new GenericEntity<Collection<LocationDto>>(locationDtos) {}).build();
    }



}
