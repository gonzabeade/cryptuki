package ar.edu.itba.paw.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="payment_methods_at_offer")
public class PaymentMethodAtOffer implements Serializable {
    @Id
    @Column(name="offer_id",nullable = false)
    private int offerId;

    @Id
    @Column(name="payment_code",nullable = false,length = 5)
    private String paymentCode;

    PaymentMethodAtOffer(){}

    PaymentMethodAtOffer(Integer offerId,String paymentCode){
        this.offerId=offerId;
        this.paymentCode=paymentCode;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
}
