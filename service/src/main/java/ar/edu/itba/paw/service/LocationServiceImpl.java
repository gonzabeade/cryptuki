package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Country;
import ar.edu.itba.paw.persistence.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationDao locationDao;

    @Autowired
    public LocationServiceImpl(final LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    public Collection<Country> getAllCountries() {
        return locationDao.getAllCountries();
    }
}
