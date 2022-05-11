package ar.edu.itba.paw.service.mailing;

import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.MessageSenderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.Locale;

@Service
public class ThymeleafMessageSenderFacade implements MessageSenderFacade {

    private final TemplateEngine templateEngine;
    private final ContactService<MailMessage> mailMessageContactService;

    @Autowired
    public ThymeleafMessageSenderFacade(TemplateEngine templateEngine, ContactService<MailMessage> mailMessageContactService) {
        this.templateEngine = templateEngine;
        this.mailMessageContactService = mailMessageContactService;
    }

    @Override
    public void sendWelcomeMessage(String username, int veryCode) {

    }

    @Override
    public void sendChangePasswordMessage(String username, int code) {
        MailMessage message = mailMessageContactService.createMessage(getUserInformation(username).get().getEmail());
        ChangePasswordThymeleafMailMessage changePasswordThymeleafMailMessage= new ChangePasswordThymeleafMailMessage(message, mailProcessor);
        changePasswordThymeleafMailMessage.setSubject(messageSource.getMessage("changePasswordSubject", null, Locale.ENGLISH));
        changePasswordThymeleafMailMessage.setParameters(username);
        contactService.sendMessage(message);
    }

    @Override
    public void sendOfferUploadedMessage(String username, String coinCode, float askingPrice, float minQuantity, float maxQuantity, int offerId) {

    }

    @Override
    public void sendComplaintReceipt(String username, String question) {

    }

    @Override
    public void sendNewTradeNotification(String username, String coinCode, String quantity, String buyer, String wallet, String buyerMail, String tradeCode) {

    }
}
