package bg.softuni.vetclinic.model.service;

public class UserRegistrationServiceModel {

    private String email;
    private String fullName;
    private String password;
    private Long phoneNumber;


    public String getEmail() {
        return email;
    }

    public UserRegistrationServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegistrationServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegistrationServiceModel setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
