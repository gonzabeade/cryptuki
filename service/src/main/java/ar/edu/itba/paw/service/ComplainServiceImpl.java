package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.parameterObject.ComplainPO;
import ar.edu.itba.paw.persistence.ComplainDao;
import ar.edu.itba.paw.persistence.UserAuthDao;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Service
public class ComplainServiceImpl implements ComplainService{

    private final ComplainDao complainDao;
    private final UserAuthDao userAuthDao;
    private final MessageSenderFacade messageSenderFacade;

    @Autowired
    public ComplainServiceImpl(ComplainDao complainDao, MessageSenderFacade messageSenderFacade, UserAuthDao userAuthDao) {
        this.complainDao = complainDao;
        this.messageSenderFacade = messageSenderFacade;
        this.userAuthDao = userAuthDao;
    }

    @Override
    @Transactional
    @Secured("ROLE_USER")
    @PreAuthorize("#complain.complainerUsername == authentication.principal.username and @customPreAuthorizer.isUserPartOfTrade(#complain.tradeId, authentication.principal)")
    public void makeComplain(ComplainPO complain) {

        if (complain == null)
            throw new NullPointerException("Complain Builder object cannot be null.");
        Complain createdComplain;
        try {
            createdComplain = complainDao.makeComplain(complain);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

        messageSenderFacade.sendComplaintReceipt(createdComplain.getComplainer(), createdComplain.getTrade(), createdComplain.getComplainerComments().orElse("No comments"));
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
    public void closeComplainWithKickout(int complainId, String moderatorUsername, String comment, int kickedUserId){
       Complain complain = complainDao.closeComplain(complainId, moderatorUsername, comment).orElseThrow(()->new NoSuchComplainException(complainId));
       userAuthDao.kickoutUser(kickedUserId);

       User  kickedOutUser;
       Trade trade = complain.getTrade();

       if(complain.getComplainer().getId() == trade.getBuyer().getId()){
           kickedOutUser = trade.getOffer().getSeller();
       }else{
           kickedOutUser = trade.getBuyer();
       }
       messageSenderFacade.sendYouWereKickedOutBecause(kickedOutUser, comment);
       messageSenderFacade.sendComplainClosedWithKickout(complain.getComplainer(), comment);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void closeComplainWithDismiss(int complainId, String moderatorUsername, String comment){
        Complain complain = complainDao.closeComplain(complainId, moderatorUsername, comment).orElseThrow(()->new NoSuchComplainException(complainId));
        messageSenderFacade.sendComplainClosedWithDismission(complain.getComplainer(), comment);
    }

    @Override
    public void getSupportFor(String email, String description, Locale locale) {
        messageSenderFacade.sendAnonymousComplaintReceipt(email, description, locale);
    }


}
