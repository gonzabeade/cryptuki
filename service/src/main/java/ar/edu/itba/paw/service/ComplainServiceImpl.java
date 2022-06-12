package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainFilter;import ar.edu.itba.paw.model.Country;
import ar.edu.itba.paw.parameterObject.ComplainPO;
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
    @Transactional
    @Secured("ROLE_USER")
    @PreAuthorize("#complain.complainerUsername == authentication.principal.username and @customPreAuthorizer.isUserPartOfTrade(#complain.tradeId, authentication.principal)")
    public void makeComplain(ComplainPO complain) {

        if (complain == null)
            throw new NullPointerException("Complain Builder object cannot be null.");

        try {
            complainDao.makeComplain(complain);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

//        messageSenderFacade.sendComplaintReceipt(complain.getComplainer(), complain.getComplainerComments());
    }

    @Override
    @Transactional(readOnly = true)
//    @Secured("ROLE_ADMIN")
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
    public long countComplainsBy(ComplainFilter filter) {

        if (filter == null)
            throw new NullPointerException("Filter object cannot be null.");

        try {
            return complainDao.getComplainCount(filter);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Complain> getComplainById(int id) {

        if (id < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        Collection<Complain> c = getComplainsBy(new ComplainFilter().restrictedToComplainId(id));
        return c.isEmpty() ? Optional.empty() : Optional.of(c.iterator().next());
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void closeComplain(int complainId, String moderatorUsername, String comment){
       Optional<Complain> maybeComplain = complainDao.closeComplain(complainId, moderatorUsername, comment);
       if (!maybeComplain.isPresent())
           throw new NoSuchComplainException(complainId);
    }

    @Override
    public void getSupportFor(String email, String description) {
        messageSenderFacade.sendAnonymousComplaintReceipt(email, email, description);
    }


}
