package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainFilter;import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.model.Country;
import ar.edu.itba.paw.parameterObject.ComplainPO;

import java.util.Collection;
import java.util.Optional;

public interface ComplainService {

    /** Complain creation */
    void makeComplain(ComplainPO complain);

    /** Complain getters and count */
    Collection<Complain> getComplainsBy(ComplainFilter filter);
    long countComplainsBy(ComplainFilter filter);
    Optional<Complain> getComplainById(int id);

    /** Close a complain */
    void closeComplain(int complainId, String moderatorUsername, String comment);

    /** Get support for anonymous users*/
    void getSupportFor(String email, String description);
}
