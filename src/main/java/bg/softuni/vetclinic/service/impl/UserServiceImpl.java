package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.DoctorEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.model.service.DoctorRegistrationServiceModel;
import bg.softuni.vetclinic.model.service.UserRegistrationServiceModel;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.repositories.UserRoleRepository;
import bg.softuni.vetclinic.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final VetClinicUserService vetClinicUserService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, VetClinicUserService vetClinicUserService, PasswordEncoder encoder) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.vetClinicUserService = vetClinicUserService;
        this.encoder = encoder;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {

            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity doctorRole = new UserRoleEntity().setRole(UserRole.DOCTOR);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, doctorRole, userRole));

            UserEntity admin = new UserEntity().setFullName("Admin Adminov").setEmail("admin@vet.bg").setPhoneNumber(555231345).setPassword(passwordEncoder.encode("123456"));
            UserEntity doctor = new DoctorEntity().setSpecialization("psychologist").setFullName("Dr Dolittle").setEmail("thedoc@vet.bg").setPhoneNumber(33325870).setPassword(passwordEncoder.encode("123456"));
            UserEntity user = new UserEntity().setFullName("Dummy User").setEmail("user@dummy.bg").setPhoneNumber(555875492).setPassword(passwordEncoder.encode("123456"));


            admin.setRoles(List.of(adminRole, doctorRole, userRole));
            doctor.setRoles(List.of(doctorRole, userRole));
            user.setRoles(List.of(userRole));

            userRepository.saveAll(List.of(admin, doctor, user));
        }
    }

    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel serviceModel) {
        UserEntity newUser = modelMapper.map(serviceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(serviceModel.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRole.USER).orElseThrow(() -> new IllegalStateException("USER role not found. Please seed the roles!"));
        newUser.addRole(userRole);

        newUser = userRepository.save(newUser);

        UserDetails principal = vetClinicUserService.loadUserByUsername(newUser.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, newUser.getPassword(), principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    public void registerAndLoginDoctor(DoctorRegistrationServiceModel serviceModel) {
        DoctorEntity newDoctor = modelMapper.map(serviceModel, DoctorEntity.class);
        newDoctor.setPassword(passwordEncoder.encode(serviceModel.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRole.USER).orElseThrow(() -> new IllegalStateException("USER role not found. Please seed the roles!"));
        UserRoleEntity docRole = userRoleRepository.findByRole(UserRole.DOCTOR).orElseThrow(() -> new IllegalStateException("DOCTOR role not found. Please seed the roles!"));
        newDoctor.addRole(docRole);
        newDoctor.addRole(userRole);

        newDoctor = userRepository.save(newDoctor);

        UserDetails principal = vetClinicUserService.loadUserByUsername(newDoctor.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, newDoctor.getPassword(), principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent() || userRepository.findByEmailAndRolesContains(email, UserRole.DOCTOR.name()).isPresent();

    }

    @Override
    public UserEntity findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
    }
}
