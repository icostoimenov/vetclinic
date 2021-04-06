package bg.softuni.vetclinic.service;

import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import bg.softuni.vetclinic.model.service.AppointmentServiceModel;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AppointmentService {

    void addAppointment(AppointmentServiceModel appointmentServiceModel);

    List<AppointmentEntity> allActiveAppsForDoctor(UserEntity doctorEntity, AppointmentStatus appointmentStatus);

    void setDiagnose(String diagnose, AppointmentStatus appointmentStatus, Long appId);
}
