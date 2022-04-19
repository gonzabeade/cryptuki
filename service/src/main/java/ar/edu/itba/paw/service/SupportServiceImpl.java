package ar.edu.itba.paw.service;
import ar.edu.itba.paw.service.digests.SupportDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupportServiceImpl implements SupportService {

    private final ContactService<MailMessage> mailContactService;

    @Autowired
    public SupportServiceImpl(ContactService<MailMessage> mailContactService) {
        this.mailContactService = mailContactService;
    }

    @Override
    public void getSupportFor(SupportDigest digest) { // TODO: Improve radically
        MailMessage mailMessage = mailContactService.createMessage(digest.getAuthor());
        mailMessage.setBody("Tu consulta: " + digest.getBody());
        mailMessage.setSubject("Hemos recibido tu consulta");
        mailContactService.sendMessage(mailMessage);
    }
}
