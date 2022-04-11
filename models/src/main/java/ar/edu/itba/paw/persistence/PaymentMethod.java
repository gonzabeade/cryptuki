package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class PaymentMethod {

    private final String name;
    private final String description;

    // Flyweight pattern
    private static Map<String, PaymentMethod> cache = new HashMap<>();

    private PaymentMethod(String name, String description) {
        this.name = name;
        this.description = description;
        cache.putIfAbsent(name, this);
    }

    protected static PaymentMethod getInstance(String name, String description) {
        return cache.getOrDefault(name, new PaymentMethod(name, description));
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof PaymentMethod))
            return false;
        PaymentMethod other = (PaymentMethod) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}