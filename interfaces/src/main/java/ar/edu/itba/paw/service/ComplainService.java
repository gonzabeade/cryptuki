package ar.edu.itba.paw.service;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.model.ComplainStatus;

import java.util.Collection;
import java.util.Optional;

public interface ComplainService {

    Collection<Complain> getComplainsBy(ComplainFilter filter);
    Optional<Complain> getComplainById(int id);
    int countComplainsBy(ComplainFilter filter);
    void makeComplain(Complain.Builder complain);
    void updateComplainStatus(int complainId, ComplainStatus complainStatus);
    void updateModerator(int complainId, String username);
    void updateModeratorComment(int complainId, String comments);
    void closeComplainWithComment(int complainId, String comments);
    void unassignComplain(int complainId);
    void assignComplain(int complainId, String username);
    void updateModerator(int complainId, String username, String comment);
    void getSupportFor(String email, String description);

}
