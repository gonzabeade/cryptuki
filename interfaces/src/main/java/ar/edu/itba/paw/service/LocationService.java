package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Country;

import java.util.Collection;

public interface LocationService {

    Collection<Country> getAllCountries();

}
