package bg.softuni.vetclinic.model.binding;


import bg.softuni.vetclinic.validators.FieldMatch;

import javax.validation.constraints.*;

@FieldMatch(first = "password", second = "confirmPassword")
public class UserRegistrationBindingModel {


    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;
    private Long phoneNumber;


    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    public UserRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotEmpty
    @Size(min = 5, max = 50)
    public String getFullName() {
        return fullName;
    }


    public UserRegistrationBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @NotEmpty
    @Size(min = 5, max = 20)
    public String getPassword() {
        return password;
    }

    public UserRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @NotEmpty
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @NotNull
    @Min(6)
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegistrationBindingModel setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
