package ar.edu.itba.paw.persistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="payment_methods_at_offer")
public class PaymentMethodAtOffer implements Serializable {
    @Id
    @Column(name="offer_id",nullable = false,unique = true)
    private int offerId;

    @Id
    @Column(name="payment_code",nullable = false,length = 5)
    private String paymentCode;

    @OneToOne
    @JoinColumn(name = "payment_code",insertable = false,updatable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="offer_id")
    private Offer offer;

    PaymentMethodAtOffer(){}

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    PaymentMethodAtOffer(Integer offerId, String paymentCode){
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
