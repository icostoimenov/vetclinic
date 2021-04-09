package bg.softuni.vetclinic.model.entities;

import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class AppointmentEntity extends BaseEntity{

    private String ownerName;
    private Long ownerPhone;
    private String ownerEmail;
    private String additionalInfo;
    private PetEntity pet;
    private LocalDate appointmentDate;
    private DoctorEntity doctor;
    private UserEntity creator;
    private AppointmentStatus status;



    public String getOwnerName() {
        return ownerName;
    }

    public AppointmentEntity setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public Long getOwnerPhone() {
        return ownerPhone;
    }

    public AppointmentEntity setOwnerPhone(Long ownerPhone) {
        this.ownerPhone = ownerPhone;
        return this;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public AppointmentEntity setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
        return this;
    }

    @Column(name = "additional_info", length = 1000)
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public AppointmentEntity setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }
    @ManyToOne
    public PetEntity getPet() {
        return pet;
    }

    public AppointmentEntity setPet(PetEntity pet) {
        this.pet = pet;
        return this;
    }

    @ManyToOne
    public DoctorEntity getDoctor() {
        return doctor;
    }

    public AppointmentEntity setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
        return this;
    }

    @ManyToOne
    public UserEntity getCreator() {
        return creator;
    }

    public AppointmentEntity setCreator(UserEntity creator) {
        this.creator = creator;
        return this;
    }

    @Column(name = "appointment_date", nullable = false)
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public AppointmentEntity setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public AppointmentStatus getStatus() {
        return status;
    }

    public AppointmentEntity setStatus(AppointmentStatus status) {
        this.status = status;
        return this;
    }

}
