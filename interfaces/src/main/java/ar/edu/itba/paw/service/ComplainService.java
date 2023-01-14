package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainFilter;
import ar.edu.itba.paw.model.parameterObject.ComplainPO;
import ar.edu.itba.paw.model.parameterObject.SolveComplainPO;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

public interface ComplainService {

    /** Complain creation */
    Complain makeComplain(ComplainPO complain);

    /** Complain getters and count */
    Collection<Complain> getComplainsBy(ComplainFilter filter);
    long countComplainsBy(ComplainFilter filter);
    Optional<Complain> getComplainById(int id);

    /** Close a complain */
    void closeComplain(SolveComplainPO solveComplainPO);

    /** Get support for anonymous users*/
    void getSupportFor(String email, String description, Locale locale);
}
