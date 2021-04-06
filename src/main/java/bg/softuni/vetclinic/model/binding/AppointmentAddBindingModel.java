package bg.softuni.vetclinic.model.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class AppointmentAddBindingModel {

    private String additionalInfo;
    private String appointmentDate;
    private Long petId;
    private Long docId;

    public Long getPetId() {
        return petId;
    }

    public AppointmentAddBindingModel setPetId(Long petId) {
        this.petId = petId;
        return this;
    }

    public Long getDocId() {
        return docId;
    }

    public AppointmentAddBindingModel setDocId(Long docId) {
        this.docId = docId;
        return this;
    }


    @NotEmpty
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public AppointmentAddBindingModel setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }


    @NotEmpty
    public String getAppointmentDate() {
        return appointmentDate;
    }

    public AppointmentAddBindingModel setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
        return this;
    }

}
