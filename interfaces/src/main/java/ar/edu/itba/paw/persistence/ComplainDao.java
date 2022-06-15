package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainFilter;
import ar.edu.itba.paw.model.parameterObject.ComplainPO;
import java.util.Collection;
import java.util.Optional;

public interface ComplainDao {

    /** Complain creation and manipulation */
    Complain makeComplain(ComplainPO complain);
    Complain modifyComplain(Complain complain);
    Optional<Complain> closeComplain(int complainId, String moderator, String moderatorComments);

    /** Complain getters */
    Collection<Complain> getComplainsBy(ComplainFilter filter);
    long getComplainCount(ComplainFilter filter);
}
