package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class PaymentMethod {

    private final String name;
    private final String description;

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
}