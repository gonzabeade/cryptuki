package ar.edu.itba.paw.cryptuki.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterForm {


    @Size(min=6, max= 100)
    @Email()
    @Pattern(regexp=".+@.+\\..+")
    private String email;
    @Size(min = 6, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @Pattern(regexp = "\\d{8}")
    private String phoneNumber;
    @Size(min = 6, max = 100)
    private String password;
    @Size(min = 6, max = 100)

    private String repeatPassword;
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
    public String getRepeatPassword() {
        return repeatPassword;
    }
    public void setRepeatPassword(String repeatPassword)
    {
        this.repeatPassword = repeatPassword;
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
}
