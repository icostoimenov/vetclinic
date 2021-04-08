package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import bg.softuni.vetclinic.model.entities.DoctorEntity;
import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import bg.softuni.vetclinic.model.entities.enums.PetType;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.repositories.AppointmentRepository;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.repositories.UserRoleRepository;

import java.time.LocalDate;
import java.util.List;

class AppointmentTestData {

    private long testDoctorId;
    private long testPetId;
    private long testAppointmentId;

    private UserRepository userRepository;
    private PetRepository petRepository;
    private AppointmentRepository appointmentRepository;
    private UserRoleRepository userRoleRepository;


    public AppointmentTestData(UserRepository userRepository, PetRepository petRepository,
                               AppointmentRepository appointmentRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public void init() {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setSpecialization("tester").setEmail("test@abv.bg").setPassword("123456").setFullName("Test Dummy").setPhoneNumber(8878548887985L);

        UserRoleEntity roleUser = new UserRoleEntity();
        UserRoleEntity roleDoctor = new UserRoleEntity();
        roleUser.setRole(UserRole.USER);
        roleDoctor.setRole(UserRole.DOCTOR);
        userRoleRepository.saveAll(List.of(roleDoctor, roleUser));

        doctorEntity.setRoles(List.of(roleUser, roleDoctor));
        userRepository.save(doctorEntity);
        this.testDoctorId = doctorEntity.getId();

        PetEntity petEntity = new PetEntity();
        petEntity.setName("TestPet").setAge(3).setGender("MALE").setType(PetType.CAT).setImageUrl("testimg").setOwner(doctorEntity);

        petRepository.save(petEntity);
        this.testPetId = petEntity.getId();

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setOwnerName("Test Dummy").setOwnerEmail("test@abv.bg").setOwnerPhone(8878548887985L).setAdditionalInfo("some info")
                .setDoctor(doctorEntity).setCreator(doctorEntity).setPet(petEntity).setAppointmentDate(LocalDate.now());
        appointmentEntity.setStatus(AppointmentStatus.PENDING);

        appointmentRepository.save(appointmentEntity);
        testAppointmentId = appointmentEntity.getId();

    }

    void cleanUp() {
        appointmentRepository.deleteAll();
        petRepository.deleteAll();
        userRepository.deleteAll();


    }

    public long getTestDoctorId() {
        return testDoctorId;
    }

    public long getTestPetId() {
        return testPetId;
    }

    public long getTestAppointmentId() {
        return testAppointmentId;
    }
}
