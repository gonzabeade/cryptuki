package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.ComplainFilter;
import java.util.Collection;

public interface ComplainDao {

    Collection<Complain> getComplainsBy(ComplainFilter filter);
    int countComplainsBy(ComplainFilter filter);

    void makeComplain(Complain.Builder complain);

    void updateComplainStatus(int complainId, ComplainStatus complainStatus);
    void updateModerator(int complainId, String username);
    void updateModeratorComment(int complain, String comments);

}
