package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.DoctorEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.model.service.DoctorRegistrationServiceModel;
import bg.softuni.vetclinic.model.service.UserRegistrationServiceModel;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.repositories.UserRoleRepository;
import bg.softuni.vetclinic.service.CloudinaryService;
import bg.softuni.vetclinic.service.UserService;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final VetClinicUserService vetClinicUserService;
    private final CloudinaryService cloudinaryService;

    public UserServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper, VetClinicUserService vetClinicUserService, CloudinaryService cloudinaryService) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.vetClinicUserService = vetClinicUserService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {

            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity doctorRole = new UserRoleEntity().setRole(UserRole.DOCTOR);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, doctorRole, userRole));

            UserEntity admin = new UserEntity().setFullName("Admin Adminov").setEmail("admin@vet.bg").setPhoneNumber(555231345L).setPassword(passwordEncoder.encode("123456"));
            UserEntity doctor = new DoctorEntity().setSpecialization("psychologist").setFullName("Dr Dolittle").setEmail("thedoc@vet.bg").setPhoneNumber(33325870L).setPassword(passwordEncoder.encode("123456"));
            UserEntity user = new UserEntity().setFullName("Dummy User").setEmail("user@dummy.bg").setPhoneNumber(555875492L).setPassword(passwordEncoder.encode("123456"));


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

    public void registerDoctor(DoctorRegistrationServiceModel serviceModel) throws IOException {
        if (serviceModel.getImageUrl() == null || serviceModel.getImageUrl().isEmpty()) {
            File file = new File("src/main/resources/static/img/defaults/doc-default.jpg");
            FileInputStream input = new FileInputStream(file);
            serviceModel.setImageUrl(new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input)));
        }

        MultipartFile img = serviceModel.getImageUrl();
        String imageUrl = cloudinaryService.uploadImage(img);

        DoctorEntity newDoctor = modelMapper.map(serviceModel, DoctorEntity.class);
        newDoctor.setPassword(passwordEncoder.encode(serviceModel.getPassword()));
        newDoctor.setProfilePicture(imageUrl);

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRole.USER).orElseThrow(() -> new IllegalStateException("USER role not found. Please seed the roles!"));
        UserRoleEntity docRole = userRoleRepository.findByRole(UserRole.DOCTOR).orElseThrow(() -> new IllegalStateException("DOCTOR role not found. Please seed the roles!"));
        newDoctor.addRole(docRole);
        newDoctor.addRole(userRole);


        userRepository.save(newDoctor);

    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();

    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<UserEntity> findDoctorsOnly() {

        return userRepository.findDoctorsOnly();
    }
}
