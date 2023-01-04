package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.cryptuki.annotation.validation.DuplicateEmail;
import ar.edu.itba.paw.cryptuki.annotation.validation.DuplicateUsername;
import ar.edu.itba.paw.cryptuki.annotation.validation.EqualFields;
import ar.edu.itba.paw.model.parameterObject.UserPO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterForm {


    @Size(min=6, max= 100)
    @DuplicateEmail
    @Pattern(regexp=".+@.+\\..+")
    @NotNull
    private String email;
    @Size(min = 6, max = 100)
    @DuplicateUsername
    @Pattern(regexp = "[a-zA-Z0-9]+")
    @NotNull
    private String username;

    @Pattern(regexp = "\\d{8,10}")
    @NotNull
    private String phoneNumber;
    @Size(min = 6, max = 100)
    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserPO toParameterObject() {
        return new UserPO(username, password, email, phoneNumber);
    }

}
