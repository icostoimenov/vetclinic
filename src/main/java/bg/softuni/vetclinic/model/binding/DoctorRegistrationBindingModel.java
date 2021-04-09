package bg.softuni.vetclinic.model.binding;

import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.validators.FieldMatch;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@FieldMatch(first = "password", second = "confirmPassword")
public class DoctorRegistrationBindingModel {

    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;
    private String specialization;
    private Long phoneNumber;
    private MultipartFile imageUrl;
    private List<PetEntity> patients = new ArrayList<>();


    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    public DoctorRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotEmpty
    @Size(min = 5, max = 50)
    public String getFullName() {
        return fullName;
    }


    public DoctorRegistrationBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @NotEmpty
    @Size(min = 5, max = 20)
    public String getPassword() {
        return password;
    }

    public DoctorRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @NotEmpty
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public DoctorRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @NotNull
    @Min(6)
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public DoctorRegistrationBindingModel setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @NotEmpty
    public String getSpecialization() {
        return specialization;
    }

    public DoctorRegistrationBindingModel setSpecialization(String specialization) {
        this.specialization = specialization;
        return this;
    }

    public List<PetEntity> getPatients() {
        return patients;
    }

    public DoctorRegistrationBindingModel setPatients(List<PetEntity> patients) {
        this.patients = patients;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public DoctorRegistrationBindingModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
