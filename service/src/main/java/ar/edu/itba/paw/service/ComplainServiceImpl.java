package ar.edu.itba.paw.service;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.persistence.ComplainDao;
import ar.edu.itba.paw.persistence.ComplainStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ComplainServiceImpl implements ComplainService{

    private ComplainDao complainDao;


    @Autowired
    public ComplainServiceImpl(ComplainDao complainDao) {
        this.complainDao = complainDao;
    }


    @Override
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {
        return complainDao.getComplainsBy(filter);
    }

    @Override
    public int countComplainsBy(ComplainFilter filter) {
        return complainDao.countComplainsBy(filter);
    }

    @Override
    public void makeComplain(Complain.Builder complain) {
        complainDao.makeComplain(complain);
    }

    @Override
    public void updateComplainStatus(int complainId, ComplainStatus complainStatus) {
        complainDao.updateComplainStatus(complainId, complainStatus);
    }

    @Override
    public void updateModerator(int complainId, String username) {
        complainDao.updateModerator(complainId, username);
    }

    @Override
    public void updateModeratorComment(int complain, String comments) {
        complainDao.updateModeratorComment(complain, comments);
    }
}
