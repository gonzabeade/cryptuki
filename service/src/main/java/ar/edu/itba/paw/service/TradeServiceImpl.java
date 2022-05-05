package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeDao;
import ar.edu.itba.paw.persistence.TradeStatus;
import ar.edu.itba.paw.service.digests.BuyDigest;
import ar.edu.itba.paw.service.digests.SellDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private final ContactService<MailMessage> mailContactService;
    private final OfferService offerService;

    private final TradeDao tradeDao;

    @Autowired
    public TradeServiceImpl(ContactService<MailMessage> mailContactService, OfferService offerService, TradeDao tradeDao) {
        this.mailContactService = mailContactService;
        this.offerService = offerService;
        this.tradeDao = tradeDao;
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
                .append(offerService.getOfferById(buyDigest.getOfferId()))
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
    public void executeTrade(SellDigest digest) {
    }

    @Override
    public void makeTrade(Trade.Builder trade) {
        tradeDao.makeTrade(trade);
    }

    @Override
    public void updateStatus(int tradeId, TradeStatus status) {
        tradeDao.updateStatus(tradeId, status);
    }

    @Override
    public Optional<Trade> getTradeById(int tradeId) {
        return tradeDao.getTradeById(tradeId);
    }

    @Override
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize) {
        return tradeDao.getSellingTradesByUsername(username, page, pageSize);
    }

    @Override
    public int getSellingTradesByUsernameCount(String username) {
        return tradeDao.getSellingTradesByUsernameCount(username);
    }

    @Override
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize) {
        return tradeDao.getBuyingTradesByUsername(username, page, pageSize);
    }

    @Override
    public int getBuyingTradesByUsernameCount(String username) {
        return tradeDao.getSellingTradesByUsernameCount(username);
    }
}
