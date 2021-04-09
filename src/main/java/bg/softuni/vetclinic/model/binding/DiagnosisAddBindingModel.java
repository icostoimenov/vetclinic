package bg.softuni.vetclinic.model.binding;


import javax.validation.constraints.NotEmpty;
import java.util.List;

public class DiagnosisAddBindingModel {

    private List<String> medications;
    private String doctorCommentary;


    public List<String> getMedications() {
        return medications;
    }

    public DiagnosisAddBindingModel setMedications(List<String> medications) {
        this.medications = medications;
        return this;
    }

    @NotEmpty
    public String getDoctorCommentary() {
        return doctorCommentary;
    }

    public DiagnosisAddBindingModel setDoctorCommentary(String doctorCommentary) {
        this.doctorCommentary = doctorCommentary;
        return this;
    }
}
