package ar.edu.itba.paw.service;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.persistence.ComplainDao;
import ar.edu.itba.paw.model.ComplainStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class ComplainServiceImpl implements ComplainService{

    private final ComplainDao complainDao;
    private final MessageSenderFacade messageSenderFacade;




    @Autowired
    public ComplainServiceImpl(ComplainDao complainDao, MessageSenderFacade messageSenderFacade) {
        this.complainDao = complainDao;
        this.messageSenderFacade = messageSenderFacade;
    }


    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @customPreAuthorizer.isUserAuthorized(#filter.complainerUsername.orElse(null), authentication.principal)")
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {

        if (filter == null)
            throw new NullPointerException("Filter object cannot be null.");

        try {
            return complainDao.getComplainsBy(filter);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Complain> getComplainById(int id) {

        if (id < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        Collection<Complain> c = getComplainsBy(new ComplainFilter.Builder().withComplainId(id).build());
        return c.isEmpty() ? Optional.empty() : Optional.of(c.iterator().next());
    }

    @Override
    @Transactional(readOnly = true)
    public int countComplainsBy(ComplainFilter filter) {

        if (filter == null)
            throw new NullPointerException("Filter object cannot be null.");

        try {
            return complainDao.countComplainsBy(filter);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @Secured("ROLE_USER")
    @PreAuthorize("#complain.complainer == authentication.principal.username and @customPreAuthorizer.isUserPartOfTrade(#complain.tradeId, authentication.principal)")
    public void makeComplain(Complain.Builder complain) {

        if (complain == null)
            throw new NullPointerException("Complain Builder object cannot be null.");

        try {
            complainDao.makeComplain(complain);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

        messageSenderFacade.sendComplaintReceipt(complain.getComplainer(), complain.getComplainerComments());
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void updateComplainStatus(int complainId, ComplainStatus complainStatus) {

        if (complainId < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        if (complainStatus == null)
            throw new NullPointerException("Complain status cannot be null.");

        try {
            complainDao.updateComplainStatus(complainId, complainStatus);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void updateModerator(int complainId, String username) {

        if (complainId < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        try {
            complainDao.updateModerator(complainId, username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void updateModeratorComment(int complainId, String comments) {

        if (complainId < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        try {
            complainDao.updateModeratorComment(complainId, comments);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void closeComplainWithComment(int complainId, String comments) {

        if (complainId < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        try {
            complainDao.updateComplainStatus(complainId, ComplainStatus.CLOSED);
            complainDao.updateModeratorComment(complainId, comments);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }
    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void unassignComplain(int complainId) {

        if (complainId < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        try {
            complainDao.updateComplainStatus(complainId, ComplainStatus.PENDING);
            complainDao.updateModerator(complainId, null);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void assignComplain(int complainId,String username) {

//        if (complainId < 0)
//            throw new IllegalArgumentException("Complain id can only be non negative.");
//
//        try {
//            complainDao.updateComplainStatus(complainId, ComplainStatus.);
//            complainDao.updateModerator(complainId, username);
//        } catch (PersistenceException pe) {
//            throw new ServiceDataAccessException(pe);
//        }
    }


    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void updateModerator(int complainId, String username, String comment) {
        updateModerator(complainId, username);
        updateModeratorComment(complainId, comment);
    }

    @Override
    public void getSupportFor(String email, String description) {
        messageSenderFacade.sendAnonymousComplaintReceipt(email, email, description);
    }


}
