package ar.edu.itba.paw.service;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.persistence.ComplainDao;
import ar.edu.itba.paw.persistence.ComplainStatus;
import ar.edu.itba.paw.service.digests.SupportDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class ComplainServiceImpl implements ComplainService{

    private final ComplainDao complainDao;
    private final ContactService<MailMessage> mailContactService;


    @Autowired
    public ComplainServiceImpl(ComplainDao complainDao, ContactService<MailMessage> mailContactService) {
        this.complainDao = complainDao;
        this.mailContactService = mailContactService;
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {

        if (filter == null)
            throw new NullPointerException("Filter object cannot be null.");

        try {
            return complainDao.getComplainsBy(filter);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
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
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void makeComplain(Complain.Builder complain) {

        if (complain == null)
            throw new NullPointerException("Complain Builder object cannot be null.");

        try {
            complainDao.makeComplain(complain);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void updateComplainStatus(int complainId, ComplainStatus complainStatus) {

        if (complainId < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        if (complainStatus == null)
            throw new NullPointerException("Complain status cannot be null.");

        try {
            complainDao.updateComplainStatus(complainId, complainStatus);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void updateModerator(int complainId, String username) {

        if (complainId < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        try {
            complainDao.updateModerator(complainId, username);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void updateModeratorComment(int complainId, String comments) {

        if (complainId < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");

        try {
            complainDao.updateModeratorComment(complainId, comments);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void updateModerator(int complainId, String username, String comment) {
        updateModerator(complainId, username);
        updateModeratorComment(complainId, comment);
    }

    @Override
    public void getSupportFor(SupportDigest digest) { // TODO: Improve radically
        MailMessage mailMessage = mailContactService.createMessage(digest.getAuthor());
        mailMessage.setBody("Tu consulta: " + digest.getBody());
        mailMessage.setSubject("Hemos recibido tu consulta");
        mailContactService.sendMessage(mailMessage);
    }

}
