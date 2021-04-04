package bg.softuni.vetclinic.model.service;

import bg.softuni.vetclinic.model.entities.PetEntity;

import java.util.ArrayList;
import java.util.List;

public class DoctorRegistrationServiceModel {

    private String email;
    private String fullName;
    private String password;
    private Long phoneNumber;
    private String specialization;
    private List<PetEntity> patients = new ArrayList<>();


    public String getEmail() {
        return email;
    }

    public DoctorRegistrationServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public DoctorRegistrationServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DoctorRegistrationServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public DoctorRegistrationServiceModel setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<PetEntity> getPatients() {
        return patients;
    }

    public DoctorRegistrationServiceModel setPatients(List<PetEntity> patients) {
        this.patients = patients;
        return this;
    }

    public String getSpecialization() {
        return specialization;
    }

    public DoctorRegistrationServiceModel setSpecialization(String specialization) {
        this.specialization = specialization;
        return this;
    }
}
