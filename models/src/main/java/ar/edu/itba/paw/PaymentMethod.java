package ar.edu.itba.paw;

import java.util.Objects;

public class PaymentMethod {

    private int id;
    private String name;
    private String description;

    public static class Builder {
        private int id;
        private String name;
        private String description = "";

        public Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder id(int id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String desc) { description = desc; return this; }

        public PaymentMethod build() {
            return new PaymentMethod(this);
        }
    }

    private PaymentMethod(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.name = builder.name;
    }

    public PaymentMethod(int id, String name, String description) {
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
}