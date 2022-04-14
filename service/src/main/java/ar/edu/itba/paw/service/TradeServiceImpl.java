package ar.edu.itba.paw.service;

import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

    // TODO: Autowire
    private final ContactService<MailMessage> mailContactService = new MailService("p2pcryptuki@gmail.com", "esteesuntestdepaw");
    private final OfferService offerService = new OfferServiceImpl();

    @Override
    public void executeTrade(BuyHelper buyHelper) {
        String user = buyHelper.getBuyerEmail();

        /* TODO: Do not use String concatenation ('+') hdps.
            Use StringBuilder !!
            You can then build a final String instance using StringBuilder.toString() ;
        */
        String message =  user + " ha demostrado interés en  " + offerService.getOffer(buyHelper.getOfferId()).toString();
        message+="\nQuiere comprarte " + buyHelper.getAmount() + "ARS";

        message+="\nTambién te dejó un mensaje: " + buyHelper.getComments();
        message+="\nContactalo ya por mail!";
        MailMessage mailMessage = mailContactService.createMessage(offerService.getOffer(buyHelper.getOfferId()).getSeller().getEmail());
        mailMessage.setBody(message);

        mailMessage.setSubject("Recibiste una oferta por tu publicación en Cryptuki!");
        mailContactService.sendMessage(mailMessage);
    }

    @Override
    public void executeTrade(SellHelper sellHelper) {

    }
}
