package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.LocationCountWrapper;

import java.util.Map;

public class LocationDto {

    private String locationCode;
    private long offerCount;

    public static LocationDto fromLocationCountWrapper(LocationCountWrapper locationCountWrapper) {
        LocationDto dto = new LocationDto();
        dto.locationCode = locationCountWrapper.getLocation().toString();
        dto.offerCount = locationCountWrapper.getLocationCount();
        return dto;
    }

    public static LocationDto fromMapEntry(Map.Entry<Location, Long> entry) {
        LocationDto dto = new LocationDto();
        dto.locationCode = entry.getKey().toString();
        dto.offerCount = entry.getValue();
        return dto;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public long getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(long offerCount) {
        this.offerCount = offerCount;
    }
}
