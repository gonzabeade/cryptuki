package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface OfferStatusDao {
    Optional<OfferStatus> getOfferStatusByCode(String code);
}
