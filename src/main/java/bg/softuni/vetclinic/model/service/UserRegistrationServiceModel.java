package bg.softuni.vetclinic.model.service;

public class UserRegistrationServiceModel {

    private String email;
    private String fullName;
    private String password;
    private int phoneNumber;


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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegistrationServiceModel setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
