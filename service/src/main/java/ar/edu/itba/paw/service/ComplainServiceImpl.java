package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.parameterObject.ComplainPO;
import ar.edu.itba.paw.persistence.ComplainDao;
import ar.edu.itba.paw.persistence.UserAuthDao;
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
    public Complain makeComplain(ComplainPO complain) {
        if (complain == null)
            throw new NullPointerException("Complain Parameter Object object cannot be null.");
        Complain createdComplain = complainDao.makeComplain(complain);;
        messageSenderFacade.sendComplaintReceipt(createdComplain.getComplainer(), createdComplain.getTrade(), createdComplain.getComplainerComments().orElse("No comments"));

        return createdComplain;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {
        if (filter == null)
            throw new NullPointerException("Filter object cannot be null.");
        return complainDao.getComplainsBy(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public long countComplainsBy(ComplainFilter filter) {
        if (filter == null)
            throw new NullPointerException("Filter object cannot be null.");
        return complainDao.getComplainCount(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Complain> getComplainById(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");
        return getComplainsBy(new ComplainFilter().restrictedToComplainId(id)).stream().findFirst();
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void closeComplainWithKickout(int complainId, String moderatorUsername, String comment, int kickedUserId) {

        Complain complain = complainDao.closeComplain(complainId, moderatorUsername, comment).orElseThrow(()->new NoSuchComplainException(complainId));
        userAuthDao.kickoutUser(kickedUserId);

        User kickedOutUser;
        Trade trade = complain.getTrade();

        // Choose who is to be kicked out, depending on who complained in the first place
        if (complain.getComplainer().getId() == trade.getBuyer().getId()){
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
