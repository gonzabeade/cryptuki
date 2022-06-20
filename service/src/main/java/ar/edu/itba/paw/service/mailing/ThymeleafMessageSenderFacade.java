package ar.edu.itba.paw.service.mailing;

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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.Locale;

@Service
public class ThymeleafMessageSenderFacade implements MessageSenderFacade {

    private final TemplateEngine templateEngine;
    private final ContactService<MailMessage> mailMessageContactService;
    private final UserDao userDao;
    private final Environment environment;
    private MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafMessageSenderFacade.class);

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
    public void sendWelcomeMessage(User user, int veryCode) {
        MailMessage message = mailMessageContactService.createMessage(user.getEmail());
        WelcomeThymeleafMailMessage welcomeMailMessage= new WelcomeThymeleafMailMessage(message, templateEngine);

        Locale locale = user.getLocale();
        welcomeMailMessage.setSubject(messageSource.getMessage("accountVerification", null, locale));
        welcomeMailMessage.setLocale(locale);
        welcomeMailMessage.setParameters(user.getUsername().get(), veryCode, getUrl());

        mailMessageContactService.sendMessage(welcomeMailMessage);
        LOGGER.info("Welcome mail sent");
    }

    @Override
    public void sendForgotPasswordMessage(User user, int code) {
        MailMessage message = mailMessageContactService.createMessage(user.getEmail());
        ForgotPasswordThymeleafMailMessage changePasswordMailMessage= new ForgotPasswordThymeleafMailMessage(message, templateEngine);

        Locale locale = user.getLocale();
        changePasswordMailMessage.setLocale(locale);
        changePasswordMailMessage.setSubject(messageSource.getMessage("changePasswordSubject", null, locale));
        changePasswordMailMessage.setParameters(user.getUsername().get(), code, getUrl());

        mailMessageContactService.sendMessage(changePasswordMailMessage);
        LOGGER.info("Change password mail sent");
    }

    @Override
    public void sendOfferUploadedMessage(Offer offer) {
        User user = offer.getSeller();
        MailMessage message = mailMessageContactService.createMessage(user.getEmail());
        OfferUploadedThymeleafMailMessage newOfferMailMessage= new OfferUploadedThymeleafMailMessage(message, templateEngine);

        Locale locale = user.getLocale();
        newOfferMailMessage.setLocale(locale);
        newOfferMailMessage.setSubject(messageSource.getMessage("offerCreated", null, locale));
        newOfferMailMessage.setParameters(offer, getUrl());

        mailMessageContactService.sendMessage(newOfferMailMessage);
        LOGGER.info("Uploaded offer mail sent");
    }

    @Override
    public void sendAnonymousComplaintReceipt(String to, String question, Locale locale) {
        MailMessage mailMessageAnonymousComplaintReceiptThymeleafMailMessage = mailMessageContactService.createMessage(to);
        AnonymousComplaintReceiptThymeleafMailMessage questionMailMessage= new AnonymousComplaintReceiptThymeleafMailMessage(mailMessageAnonymousComplaintReceiptThymeleafMailMessage, templateEngine);

        questionMailMessage.setLocale(locale);
        questionMailMessage.setSubject(messageSource.getMessage("complaintReceived", null, locale));
        questionMailMessage.setParameters(question, getUrl());
        mailMessageContactService.sendMessage(questionMailMessage);
        LOGGER.info("Complaint receipt sent to Anonymous");
    }

    @Override
    public void sendComplaintReceipt(User user, Trade trade, String complaint) {
        MailMessage mailMessage = mailMessageContactService.createMessage(user.getEmail());
        ComplaintReceiptThymeleafMailMessage questionMailMessage= new ComplaintReceiptThymeleafMailMessage(mailMessage, templateEngine);

        Locale locale = user.getLocale();
        questionMailMessage.setLocale(locale);
        questionMailMessage.setSubject(messageSource.getMessage("complaintReceivedSubject", null, locale));
        questionMailMessage.setParameters(trade, complaint, getUrl());

        mailMessageContactService.sendMessage(questionMailMessage);
        LOGGER.info("Complaint receipt sent to user");
    }

    @Override
    public void sendComplainClosedWithKickout(User user, String reason) {
        MailMessage mailMessage = mailMessageContactService.createMessage(user.getEmail());
        ComplaintClosedWithKickoutThymeleafMailMessage complaintClosedWithKickoutThymeleafMailMessage = new ComplaintClosedWithKickoutThymeleafMailMessage(mailMessage, templateEngine);

        Locale locale = user.getLocale();
        complaintClosedWithKickoutThymeleafMailMessage.setLocale(locale);
        complaintClosedWithKickoutThymeleafMailMessage.setSubject(messageSource.getMessage("complaintResult", null, locale));
        complaintClosedWithKickoutThymeleafMailMessage.setParameters(reason, getUrl());

        mailMessageContactService.sendMessage(complaintClosedWithKickoutThymeleafMailMessage);
        LOGGER.info("Complaint with kickout result sent to user");
    }

    @Override
    public void sendComplainClosedWithDismission(User user, String reason) {
        MailMessage mailMessage = mailMessageContactService.createMessage(user.getEmail());
        ComplaintClosedWithDismissionThymeleafMailMessage complaintClosedWithDismissionThymeleafMailMessage = new ComplaintClosedWithDismissionThymeleafMailMessage(mailMessage, templateEngine);

        Locale locale = user.getLocale();
        complaintClosedWithDismissionThymeleafMailMessage.setLocale(locale);
        complaintClosedWithDismissionThymeleafMailMessage.setSubject(messageSource.getMessage("complaintResult", null, locale));
        complaintClosedWithDismissionThymeleafMailMessage.setParameters(reason, getUrl());

        mailMessageContactService.sendMessage(complaintClosedWithDismissionThymeleafMailMessage);
        LOGGER.info("Complaint with dismission result sent to user");
    }

    @Override
    public void sendYouWereKickedOutBecause(User user, String reason) {
        MailMessage mailMessage = mailMessageContactService.createMessage(user.getEmail());
        YouWereKickedOutBecauseThymeleafMailMessage youWereKickedOutBecauseThymeleafMailMessage = new YouWereKickedOutBecauseThymeleafMailMessage(mailMessage, templateEngine);

        Locale locale = user.getLocale();
        youWereKickedOutBecauseThymeleafMailMessage.setLocale(locale);
        youWereKickedOutBecauseThymeleafMailMessage.setSubject(messageSource.getMessage("kickedOut", null, locale));
        youWereKickedOutBecauseThymeleafMailMessage.setParameters(getUrl(),reason);

        mailMessageContactService.sendMessage(youWereKickedOutBecauseThymeleafMailMessage);
        LOGGER.info("Kickout notification sent to banned user");

    }

    @Override
    public void sendNewTradeNotification(Trade trade) {
        User seller = trade.getOffer().getSeller();
        MailMessage mailMessage = mailMessageContactService.createMessage(seller.getEmail());
        NewTradeNotificationThymeleafMailMessage tradeClosedMailMessage= new NewTradeNotificationThymeleafMailMessage(mailMessage, templateEngine);

        Locale locale = seller.getLocale();
        tradeClosedMailMessage.setLocale(locale);
        tradeClosedMailMessage.setSubject(messageSource.getMessage("tradeOpenedSubject", null, locale));
        tradeClosedMailMessage.setParameters(trade, getUrl());

        mailMessageContactService.sendMessage(tradeClosedMailMessage);
        LOGGER.info("Received Trade notification sent");
    }

    @Override
    public void sendNewUnseenMessages(Trade trade, User user) {
        MailMessage mailMessage = mailMessageContactService.createMessage(user.getEmail());
        NewUnseenMessagesThymeleafMailMessage newUnseenMessagesThymeleafMailMessage = new NewUnseenMessagesThymeleafMailMessage(mailMessage, templateEngine);

        Locale locale = user.getLocale();
        newUnseenMessagesThymeleafMailMessage.setLocale(locale);
        newUnseenMessagesThymeleafMailMessage.setSubject(messageSource.getMessage("newUnseenMessages", null, locale));
        newUnseenMessagesThymeleafMailMessage.setParameters(getUrl(), trade.getTradeId());

        mailMessageContactService.sendMessage(newUnseenMessagesThymeleafMailMessage);
        LOGGER.info("Unseen messages notification sent to user");
    }

    @Override
    public void sendIdentityVerified(User user) {
        MailMessage mailMessage = mailMessageContactService.createMessage(user.getEmail());
        IdentityVerifiedThymeleafMailMessage identityVerifiedThymeleafMailMessage = new IdentityVerifiedThymeleafMailMessage(mailMessage, templateEngine);

        Locale locale = user.getLocale();
        identityVerifiedThymeleafMailMessage.setLocale(locale);
        identityVerifiedThymeleafMailMessage.setSubject(messageSource.getMessage("kycSuccessful", null, locale));
        identityVerifiedThymeleafMailMessage.setParameters(getUrl());

        mailMessageContactService.sendMessage(identityVerifiedThymeleafMailMessage);
        LOGGER.info("KYC OK result sent to user");

    }

    @Override
    public void sendIdentityRequestRejected(User user, String reason) {
        MailMessage mailMessage = mailMessageContactService.createMessage(user.getEmail());
        IdentityNotVerifiedThymeleafMailMessage identityNotVerifiedThymeleafMailMessage = new IdentityNotVerifiedThymeleafMailMessage(mailMessage, templateEngine);

        Locale locale = user.getLocale();
        identityNotVerifiedThymeleafMailMessage.setLocale(locale);
        identityNotVerifiedThymeleafMailMessage.setSubject(messageSource.getMessage("kycUnsuccessful", null, locale));
        identityNotVerifiedThymeleafMailMessage.setParameters(getUrl(), reason);

        mailMessageContactService.sendMessage(identityNotVerifiedThymeleafMailMessage);
        LOGGER.info("KYC rejected result  sent to user");

    }
}
