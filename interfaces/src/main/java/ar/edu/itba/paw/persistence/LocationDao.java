package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Country;

import java.util.Collection;

public interface LocationDao {

    Collection<Country> getAllCountries();

}
