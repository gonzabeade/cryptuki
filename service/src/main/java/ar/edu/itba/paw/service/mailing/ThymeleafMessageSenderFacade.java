package ar.edu.itba.paw.service.mailing;

import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.MessageSenderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.Locale;

@Service
public class ThymeleafMessageSenderFacade implements MessageSenderFacade {

    private final TemplateEngine templateEngine;
    private final ContactService<MailMessage> mailMessageContactService;
    private MessageSource messageSource;

    @Autowired
    public ThymeleafMessageSenderFacade(TemplateEngine templateEngine, ContactService<MailMessage> mailMessageContactService, @Qualifier("mailingMessageSource") MessageSource messageSource) {
        this.templateEngine = templateEngine;
        this.mailMessageContactService = mailMessageContactService;
        this.messageSource = messageSource;
    }

    @Override
    public void sendWelcomeMessage(String to, String username, int veryCode) {
        MailMessage message = mailMessageContactService.createMessage(to);
        WelcomeThymeleafMailMessage welcomeMailMessage= new WelcomeThymeleafMailMessage(message, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        welcomeMailMessage.setSubject(messageSource.getMessage("accountVerification", null, locale));
        welcomeMailMessage.setLocale(locale);
        welcomeMailMessage.setParameters(username, veryCode);
        mailMessageContactService.sendMessage(message);
    }

    @Override
    public void sendChangePasswordMessage(String to, String username, int code) {
        MailMessage message = mailMessageContactService.createMessage(to);
        ChangePasswordThymeleafMailMessage changePasswordMailMessage= new ChangePasswordThymeleafMailMessage(message, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        changePasswordMailMessage.setLocale(locale);
        changePasswordMailMessage.setSubject(messageSource.getMessage("changePasswordSubject", null, locale));
        changePasswordMailMessage.setParameters(username);
        mailMessageContactService.sendMessage(message);
    }

    @Override
    public void sendOfferUploadedMessage(String to, String username, String coinCode, double askingPrice, double minQuantity, double maxQuantity, int offerId) {
        MailMessage message = mailMessageContactService.createMessage(to);
        NewOfferThymeleafMailMessage newOfferMailMessage= new NewOfferThymeleafMailMessage(message, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        newOfferMailMessage.setLocale(locale);
        newOfferMailMessage.setSubject(messageSource.getMessage("offerCreated", null, locale));
        newOfferMailMessage.setParameters(username,coinCode,askingPrice,minQuantity,maxQuantity);
        mailMessageContactService.sendMessage(message);

    }

    @Override
    public void sendComplaintReceipt(String to, String username, String question) {
        MailMessage mailMessage = mailMessageContactService.createMessage(to);
        QuestionThymeleafMailMessage questionMailMessage= new QuestionThymeleafMailMessage(mailMessage, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        questionMailMessage.setLocale(locale);
        questionMailMessage.setSubject(messageSource.getMessage("complaintReceived", null, locale));
        questionMailMessage.setParameters(username, question);
        mailMessageContactService.sendMessage(questionMailMessage);
    }

    @Override
    public void sendNewTradeNotification(String to, String username, String coinCode, float quantity, String buyer, String wallet, String buyerMail, int tradeCode){
        MailMessage mailMessage = mailMessageContactService.createMessage(to);
        TradeClosedThymeleafMailMessage tradeClosedMailMessage= new TradeClosedThymeleafMailMessage(mailMessage, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        tradeClosedMailMessage.setLocale(locale);
        tradeClosedMailMessage.setSubject(messageSource.getMessage("tradeOpenedSubject", null, locale));
        tradeClosedMailMessage.setParameters(username, coinCode, quantity, buyer, wallet, buyerMail, tradeCode);
        mailMessageContactService.sendMessage(tradeClosedMailMessage);
    }
}
