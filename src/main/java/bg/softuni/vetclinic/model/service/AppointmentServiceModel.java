package bg.softuni.vetclinic.model.service;

import java.time.LocalDate;

public class AppointmentServiceModel {

    private String additionalInfo;
    private Long petId;
    private LocalDate appointmentDate;
    private Long doctorId;
    private String creator;


    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public AppointmentServiceModel setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }


    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public AppointmentServiceModel setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
        return this;
    }

    public Long getPetId() {
        return petId;
    }

    public AppointmentServiceModel setPetId(Long petId) {
        this.petId = petId;
        return this;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public AppointmentServiceModel setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public AppointmentServiceModel setCreator(String creator) {
        this.creator = creator;
        return this;
    }
}
