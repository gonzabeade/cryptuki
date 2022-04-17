package ar.edu.itba.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

    private final ContactService<MailMessage> mailContactService;
    private final OfferService offerService;

    @Autowired
    public TradeServiceImpl(ContactService<MailMessage> mailContactService, OfferService offerService) {
        this.mailContactService = mailContactService;
        this.offerService = offerService;
    }

    @Override
    public void executeTrade(BuyDigest buyDigest) {

        String buyerEmail = buyDigest.getBuyerEmail();

        /* TODO: Do not use String concatenation ('+') hdps.
            Use StringBuilder !!
            You can then build a final String instance using StringBuilder.toString()
            (Source: Item 63 - Effective Java)
        */

        String sellerMessage = new StringBuilder()
                .append(buyerEmail)
                .append(" ha demostrado interés en  ")
                .append(offerService.getOffer(buyDigest.getOfferId()))
                .append("\nQuiere comprarte ")
                .append(buyDigest.getAmount())
                .append("ARS")
                .append("\nTambién te dejó un mensaje: ")
                .append(buyDigest.getComments())
                .append("\nContactalo ya por mail!")
                .toString();


        MailMessage mailMessage = mailContactService.createMessage(buyDigest.getBuyerEmail());
        mailMessage.setBody(buyDigest.getComments());
        mailMessage.setSubject("Has solicitado comprarle a un vendedor de Cryptuki!");
        mailContactService.sendMessage(mailMessage);
    }

    @Override
    public void executeTrade(SellHelper sellHelper) {

    }
}
