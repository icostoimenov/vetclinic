package bg.softuni.vetclinic.service;

import bg.softuni.vetclinic.model.entities.DoctorEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.DoctorRegistrationServiceModel;
import bg.softuni.vetclinic.model.service.UserRegistrationServiceModel;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegistrationServiceModel serviceModel);
    void registerDoctor(DoctorRegistrationServiceModel serviceModel) throws IOException;

    boolean emailExists(String email);

    UserEntity findByEmail(String email);

    List<UserEntity> findDoctorsOnly();
    List<DoctorEntity> findAllDoctors();
}
