package ar.edu.itba.paw.parameterObject;

import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.User;

public class ComplainPO {

    private int tradeId;

    private ComplainStatus status = ComplainStatus.PENDING;

    private String complainerComments = "";

    private String complainerUsername;

    public ComplainPO(int tradeId, String complainerUsername) {
        this.tradeId = tradeId;
        this.complainerUsername = complainerUsername;
    }

    public ComplainPO withComplainerComments(String complainerComments) {
        this.complainerComments = complainerComments;
        return this;
    }

    public ComplainPO withStatus(ComplainStatus status) {
        this.status = status;
        return this;
    }

    public ComplainPO withStatus(String status) {
        this.status = ComplainStatus.valueOf(status);
        return this;
    }

    public int getTradeId() {
        return tradeId;
    }

    public ComplainStatus getStatus() {
        return status;
    }

    public String getComplainerComments() {
        return complainerComments;
    }

    public String getComplainerUsername() {
        return complainerUsername;
    }

    public Complain.Builder toBuilder(Trade trade, User complainer, User moderator) {
        if (trade == null || complainer == null || trade.getTradeId() != tradeId || !complainer.getUsername().equals(complainerUsername))
            throw new IllegalArgumentException("Trade or complainer  different than the one on the parameter object");

        return new Complain.Builder(trade, complainer)
                .withModerator(moderator)
                .withComplainerComments(complainerComments)
                .withStatus(status);
    }
}
