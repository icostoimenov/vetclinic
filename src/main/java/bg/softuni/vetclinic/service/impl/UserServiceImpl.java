package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.DoctorEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.enums.UserRole;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.repositories.UserRoleRepository;
import bg.softuni.vetclinic.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {

            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity doctorRole = new UserRoleEntity().setRole(UserRole.DOCTOR);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, doctorRole, userRole));

            UserEntity admin = new UserEntity().setUsername("admin").setFullName("Admin Adminov").setEmail("admin@vet.bg").setPhoneNumber(555231345).setPassword(passwordEncoder.encode("123456"));
            UserEntity doctor = new DoctorEntity().setSpecialization("psychologist").setUsername("doctor").setFullName("Dr Dolittle").setEmail("thedoc@vet.bg").setPhoneNumber(33325870).setPassword(passwordEncoder.encode("123456"));
            UserEntity user = new UserEntity().setUsername("user").setFullName("Dummy User").setEmail("user@dummy.bg").setPhoneNumber(555875492).setPassword(passwordEncoder.encode("123456"));


            admin.setRoles(List.of(adminRole, doctorRole, userRole));
            doctor.setRoles(List.of(doctorRole, userRole));
            user.setRoles(List.of(userRole));

            userRepository.saveAll(List.of(admin, doctor, user));
        }
    }
}
