package ar.edu.itba.paw.cryptuki.form.support;

import ar.edu.itba.paw.model.parameterObject.ComplainPO;

import javax.validation.constraints.NotNull;

public class TradeComplainSupportForm extends GeneralSupportForm {

    @NotNull
    private Integer tradeId;
    @NotNull
    private String username;

    public String getUsername() {
        return username;
    }
    public Integer getTradeId() {
        return tradeId;
    }


    public void setUsername(String username) {
        this.username = username;
    }
    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }


    public ComplainPO toComplainPO(String username){
        return new ComplainPO(tradeId, username)
                .withComplainerComments(getMessage());
    }


}
