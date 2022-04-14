package ar.edu.itba.paw.service;
import org.springframework.stereotype.Service;

@Service
public class SupportServiceImpl implements SupportService {

    // TODO: Autowire
    private final ContactService<MailMessage> mailContactService = new MailService("p2pcryptuki@gmail.com", "esteesuntestdepaw");

    @Override
    public void getSupportFor(Helper helper) { // TODO: Improve radically
        MailMessage mailMessage = mailContactService.createMessage(helper.getAuthor());
        mailMessage.setBody("Tu consulta: " + helper.getBody());
        mailMessage.setSubject("Hemos recibido tu consulta");
        mailContactService.sendMessage(mailMessage);
    }
}
