package bg.softuni.vetclinic.model.view;

import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;

import javax.persistence.Column;

public class AppointmentViewModel {

    private String ownerName;
    private Long ownerPhone;
    private String ownerEmail;
    private String additionalInfo;
    private Long petId;
    private String petName;
    private String appointmentDate;
    private AppointmentStatus status;
    private String doctorCommentary;

    public String getOwnerName() {
        return ownerName;
    }

    public AppointmentViewModel setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public Long getOwnerPhone() {
        return ownerPhone;
    }

    public AppointmentViewModel setOwnerPhone(Long ownerPhone) {
        this.ownerPhone = ownerPhone;
        return this;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public AppointmentViewModel setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
        return this;
    }

    @Column()
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public AppointmentViewModel setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }


    public String getAppointmentDate() {
        return appointmentDate;
    }

    public AppointmentViewModel setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
        return this;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public AppointmentViewModel setStatus(AppointmentStatus status) {
        this.status = status;
        return this;
    }

    public String getDoctorCommentary() {
        return doctorCommentary;
    }

    public AppointmentViewModel setDoctorCommentary(String doctorCommentary) {
        this.doctorCommentary = doctorCommentary;
        return this;
    }

    public Long getPetId() {
        return petId;
    }

    public AppointmentViewModel setPetId(Long petId) {
        this.petId = petId;
        return this;
    }

    public String getPetName() {
        return petName;
    }

    public AppointmentViewModel setPetName(String petName) {
        this.petName = petName;
        return this;
    }
}
