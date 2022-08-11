package ar.edu.itba.paw.model.parameterObject;

import java.util.Locale;

public class UserPO {

    private String email;
    private String username;
    private String plainPassword;
    private String phoneNumber;
    private Locale locale = Locale.US;

    public UserPO(String username, String plainPassword, String email, String phoneNumber) {
        this.username = username;
        this.plainPassword = plainPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserPO withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Locale getLocale() {
        return locale;
    }
}
