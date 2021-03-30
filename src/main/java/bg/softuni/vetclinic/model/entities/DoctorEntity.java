package bg.softuni.vetclinic.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class DoctorEntity extends UserEntity {

    private String specialization;
    private List<PetEntity> patients = new ArrayList<>();

    @Column(nullable = false)
    public String getSpecialization() {
        return specialization;
    }

    public DoctorEntity setSpecialization(String specialization) {
        this.specialization = specialization;
        return this;
    }

    @OneToMany
    public List<PetEntity> getPatients() {
        return patients;
    }

    public DoctorEntity setPatients(List<PetEntity> patients) {
        this.patients = patients;
        return this;
    }
}
