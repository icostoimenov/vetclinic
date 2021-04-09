package bg.softuni.vetclinic.model.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class DiagnosisEntity extends BaseEntity {

    private String doctorName;
    private LocalDate diagnoseDate;
    private PetEntity patient;
    private Set<String> medications;
    private String doctorCommentary;

    @Column(name = "doctor_name")
    public String getDoctorName() {
        return doctorName;
    }

    public DiagnosisEntity setDoctorName(String doctorName) {
        this.doctorName = doctorName;
        return this;
    }

    @Column(name = "diagnose_date")
    public LocalDate getDiagnoseDate() {
        return diagnoseDate;
    }

    public DiagnosisEntity setDiagnoseDate(LocalDate diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
        return this;
    }

    @ManyToOne
    public PetEntity getPatient() {
        return patient;
    }

    public DiagnosisEntity setPatient(PetEntity patient) {
        this.patient = patient;
        return this;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    public Set<String> getMedications() {
        return medications;
    }

    public DiagnosisEntity setMedications(Set<String> medications) {
        this.medications = medications;
        return this;
    }
    @Column(name = "doctor_commentary")
    public String getDoctorCommentary() {
        return doctorCommentary;
    }

    public DiagnosisEntity setDoctorCommentary(String doctorCommentary) {
        this.doctorCommentary = doctorCommentary;
        return this;
    }
}
