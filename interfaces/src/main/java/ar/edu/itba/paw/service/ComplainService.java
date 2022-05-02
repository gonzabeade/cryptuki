package ar.edu.itba.paw.service;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.persistence.ComplainStatus;

import java.util.Collection;

public interface ComplainService {

    Collection<Complain> getComplainsBy(ComplainFilter filter);
    int countComplainsBy(ComplainFilter filter);

    void makeComplain(Complain.Builder complain);

    void updateComplainStatus(int complainId, ComplainStatus complainStatus);
    void updateModerator(int complainId, String username);
    void updateModeratorComment(int complain, String comments);
}
