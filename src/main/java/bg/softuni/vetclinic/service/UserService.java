package bg.softuni.vetclinic.service;

import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.DoctorRegistrationServiceModel;
import bg.softuni.vetclinic.model.service.UserRegistrationServiceModel;

import java.util.List;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegistrationServiceModel serviceModel);
    void registerAndLoginDoctor(DoctorRegistrationServiceModel serviceModel);

    boolean emailExists(String email);

    UserEntity findByEmail(String email);

    List<UserEntity> findDoctorsOnly();
}
