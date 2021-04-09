package bg.softuni.vetclinic.model.service;


import java.time.LocalDate;
import java.util.List;

public class DiagnosisServiceModel {

    private String doctorEmail;
    private LocalDate diagnoseDate;
    private Long patient;
    private List<String> medications;
    private String doctorCommentary;

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public DiagnosisServiceModel setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
        return this;
    }

    public LocalDate getDiagnoseDate() {
        return diagnoseDate;
    }

    public DiagnosisServiceModel setDiagnoseDate(LocalDate diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
        return this;
    }

    public Long getPatient() {
        return patient;
    }

    public DiagnosisServiceModel setPatient(Long patient) {
        this.patient = patient;
        return this;
    }

    public List<String> getMedications() {
        return medications;
    }

    public DiagnosisServiceModel setMedications(List<String> medications) {
        this.medications = medications;
        return this;
    }

    public String getDoctorCommentary() {
        return doctorCommentary;
    }

    public DiagnosisServiceModel setDoctorCommentary(String doctorCommentary) {
        this.doctorCommentary = doctorCommentary;
        return this;
    }
}
