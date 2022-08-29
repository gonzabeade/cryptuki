package ar.edu.itba.paw.model;

import javax.persistence.SqlResultSetMapping;

// Just a Pojo!
@SqlResultSetMapping(name="LocationCountResult")
public class LocationCountWrapper {
    private Location location;
    private long locationCount;

    public LocationCountWrapper(Location location, long locationCount) {
        this.location = location;
        this.locationCount = locationCount;
    }

    public Location getLocation() {
        return location;
    }

    public long getLocationCount() {
        return locationCount;
    }
}
