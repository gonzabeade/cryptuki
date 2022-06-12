package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.parameterObject.ComplainPO;
import java.util.Collection;

public interface ComplainDao {

    /** Complain creation and manipulation */
    Complain makeComplain(ComplainPO complain);
    Complain modifyComplain(Complain complain) ;

    /** Complain getters */
    Collection<Complain> getComplainsBy(ComplainFilter filter);
    long getComplainCount(ComplainFilter filter);
}
