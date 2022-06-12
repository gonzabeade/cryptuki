package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainFilter;import ar.edu.itba.paw.parameterObject.ComplainPO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SupportForm {

    @Size(min=6, max= 100)
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @NotEmpty
    @Size(min=1, max= 140)
    private String message;

    private Integer tradeId;

    private Integer complainerId;

    public Integer getComplainerId() {
        return complainerId;
    }

    public void setComplainerId(Integer complainerId) {
        this.complainerId = complainerId;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public ComplainPO toComplainPO(String username){
        return new ComplainPO(tradeId, username)
                .withComplainerComments(message);
    }


}
