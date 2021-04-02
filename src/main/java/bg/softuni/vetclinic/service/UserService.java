package bg.softuni.vetclinic.service;

import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.DoctorRegistrationServiceModel;
import bg.softuni.vetclinic.model.service.UserRegistrationServiceModel;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegistrationServiceModel serviceModel);
    void registerAndLoginDoctor(DoctorRegistrationServiceModel serviceModel);

    boolean emailExists(String email);

    UserEntity findByEmail(String email);
}
