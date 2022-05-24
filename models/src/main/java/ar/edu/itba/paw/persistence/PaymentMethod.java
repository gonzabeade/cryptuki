package ar.edu.itba.paw.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name="payment_method")
public final class PaymentMethod {

    PaymentMethod(){}
    @Id
    @Column(name="code",length = 5)
    private  String name;
    @Column(name="payment_description",length = 20)
    private  String description;

    private static Map<String, PaymentMethod> cache = new HashMap<>();


    private PaymentMethod(String name, String description) {
        this.name = name;
        this.description = description;
        cache.putIfAbsent(name, this);
    }

    protected static PaymentMethod getInstance(String name, String description) {
        return cache.getOrDefault(name, new PaymentMethod(name, description));
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public boolean equals(Object object){
        if(object == this)
            return true;
        if(!(object instanceof PaymentMethod))
            return false;
        PaymentMethod testedPaymentMethod= (PaymentMethod) object;
        return testedPaymentMethod.getName().equals(this.getName());
    }
}