package bg.softuni.vetclinic.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AppointmentAddBindingModel {

    private String additionalInfo;
    private String appointmentDate;
    private Long petId;
    private Long docId;

    @NotNull
    public Long getPetId() {
        return petId;
    }

    public AppointmentAddBindingModel setPetId(Long petId) {
        this.petId = petId;
        return this;
    }

    @NotNull
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
