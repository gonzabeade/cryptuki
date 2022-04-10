package ar.edu.itba.paw.persistence;

import java.util.Objects;

public final class PaymentMethod {

    private final Integer id;
    private final String name;
    private final String description;

    private static

    protected PaymentMethod(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof PaymentMethod))
            return false;
        PaymentMethod other = (PaymentMethod) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}