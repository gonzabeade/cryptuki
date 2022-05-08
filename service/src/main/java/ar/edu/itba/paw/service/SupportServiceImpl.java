package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.persistence.ComplainDao;
import ar.edu.itba.paw.service.digests.SupportDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupportServiceImpl implements SupportService {

    private final ContactService<MailMessage> mailContactService;
    private final ComplainDao complainDao;

    @Autowired
    public SupportServiceImpl(ContactService<MailMessage> mailContactService,ComplainDao complainDao) {
        this.mailContactService = mailContactService;
        this.complainDao=complainDao;
    }

    @Override
    public void getSupportFor(SupportDigest digest) {
        MailMessage mailMessage = mailContactService.createMessage(digest.getAuthor());
        mailMessage.setBody("Tu consulta: " + digest.getBody());
        mailMessage.setSubject("Hemos recibido tu consulta");
        mailContactService.sendMessage(mailMessage);
    }

    @Override
    public void getSupportFor(Complain.Builder builder) {
        complainDao.makeComplain(builder);
    }
}
