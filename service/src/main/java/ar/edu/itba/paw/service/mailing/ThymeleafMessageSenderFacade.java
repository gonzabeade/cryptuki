package ar.edu.itba.paw.service.mailing;

import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserDao;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.MessageSenderFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.Locale;
import java.util.Optional;

@Service
public class ThymeleafMessageSenderFacade implements MessageSenderFacade {

    private final TemplateEngine templateEngine;
    private final ContactService<MailMessage> mailMessageContactService;
    private final UserDao userDao;
    private final Environment environment;

    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafMessageSenderFacade.class);


    private String getTo(String username) {
        Optional<User> userOptional =  userDao.getUserByUsername(username);
        if (!userOptional.isPresent())
            throw new NoSuchUserException(username);
        return userOptional.get().getEmail();
    }

    private String getUrl() {
       return environment.getProperty("webappBaseUrl");
    }

    @Autowired
    public ThymeleafMessageSenderFacade(TemplateEngine templateEngine, ContactService<MailMessage> mailMessageContactService, UserDao userDao, Environment environment, @Qualifier("mailingMessageSource") MessageSource messageSource) {
        this.templateEngine = templateEngine;
        this.mailMessageContactService = mailMessageContactService;
        this.userDao = userDao;
        this.environment = environment;
        this.messageSource = messageSource;
    }

    @Override
    public void sendWelcomeMessage(String email, String username, int veryCode) {
        MailMessage message = mailMessageContactService.createMessage(email);
        WelcomeThymeleafMailMessage welcomeMailMessage= new WelcomeThymeleafMailMessage(message, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        welcomeMailMessage.setSubject(messageSource.getMessage("accountVerification", null, locale));
        welcomeMailMessage.setLocale(locale);
        welcomeMailMessage.setParameters(username, veryCode, getUrl());
        mailMessageContactService.sendMessage(welcomeMailMessage);
        LOGGER.info("Welcome mail sent");
    }

    @Override
    public void sendChangePasswordMessage(String username, int code) {
        MailMessage message = mailMessageContactService.createMessage(getTo(username));
        ChangePasswordThymeleafMailMessage changePasswordMailMessage= new ChangePasswordThymeleafMailMessage(message, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        changePasswordMailMessage.setLocale(locale);
        changePasswordMailMessage.setSubject(messageSource.getMessage("changePasswordSubject", null, locale));
        changePasswordMailMessage.setParameters(username, code, getUrl());
        mailMessageContactService.sendMessage(changePasswordMailMessage);
        LOGGER.info("Change password mail sent");
    }

    @Override
    public void sendOfferUploadedMessage(String username, Offer offer) {
        MailMessage message = mailMessageContactService.createMessage(getTo(username));
        NewOfferThymeleafMailMessage newOfferMailMessage= new NewOfferThymeleafMailMessage(message, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        newOfferMailMessage.setLocale(locale);
        newOfferMailMessage.setSubject(messageSource.getMessage("offerCreated", null, locale));
        newOfferMailMessage.setParameters(
                username,
                offer.getCrypto().getCode(),
                offer.getLocation().toString(),
                offer.getDate().toString(),
                offer.getMaxInCrypto(),
                offer.getOfferId(),
                getUrl());
        mailMessageContactService.sendMessage(newOfferMailMessage);
        LOGGER.info("Uploaded offer mail sent");
    }

    @Override
    public void sendAnonymousComplaintReceipt(String to, String username, String question) {
        MailMessage mailMessage = mailMessageContactService.createMessage(to);
        QuestionThymeleafMailMessage questionMailMessage= new QuestionThymeleafMailMessage(mailMessage, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        questionMailMessage.setLocale(locale);
        questionMailMessage.setSubject(messageSource.getMessage("complaintReceived", null, locale));
        questionMailMessage.setParameters(username, question, getUrl());
        mailMessageContactService.sendMessage(questionMailMessage);
        LOGGER.info("Complaint receipt sent to Anonymous");
    }

    @Override
    public void sendComplaintReceipt(String username, String question) {
        MailMessage mailMessage = mailMessageContactService.createMessage(getTo(username));
        ComplaintThymeleafMailMessage questionMailMessage= new ComplaintThymeleafMailMessage(mailMessage, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        questionMailMessage.setLocale(locale);
        questionMailMessage.setSubject(messageSource.getMessage("complaintReceivedSubject", null, locale));
        questionMailMessage.setParameters(username, question, getUrl());
        mailMessageContactService.sendMessage(questionMailMessage);
        LOGGER.info("Complaint receipt sent to user");
    }

    @Override
    public void sendNewTradeNotification(String username, Trade trade, int tradeId,int offerId){
        MailMessage mailMessage = mailMessageContactService.createMessage(getTo(username));
        TradeClosedThymeleafMailMessage tradeClosedMailMessage= new TradeClosedThymeleafMailMessage(mailMessage, templateEngine);
        Locale locale = LocaleContextHolder.getLocale();
        tradeClosedMailMessage.setLocale(locale);
        tradeClosedMailMessage.setSubject(messageSource.getMessage("tradeOpenedSubject", null, locale));
        tradeClosedMailMessage.setParameters(
                username,
                trade.getOffer().getCrypto().getCode(),
                trade.getQuantity(),
                trade.getBuyer().getUsername().get(),
                tradeId,
                getUrl(),
                offerId);
        mailMessageContactService.sendMessage(tradeClosedMailMessage);
        LOGGER.info("Received Trade notification sent");
    }
}
