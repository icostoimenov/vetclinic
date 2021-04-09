package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.entities.*;
import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import bg.softuni.vetclinic.model.entities.enums.PetType;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

class AppointmentTestData {

    private long testDoctorId;
    private long testPetId;
    private long testAppointmentId;
    private long testDiagnosisId;

    private UserRepository userRepository;
    private PetRepository petRepository;
    private AppointmentRepository appointmentRepository;
    private UserRoleRepository userRoleRepository;
    private DiagnosisRepository diagnosisRepository;


    public AppointmentTestData(UserRepository userRepository, PetRepository petRepository,
                               AppointmentRepository appointmentRepository, UserRoleRepository userRoleRepository, DiagnosisRepository diagnosisRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRoleRepository = userRoleRepository;
        this.diagnosisRepository = diagnosisRepository;
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

        DiagnosisEntity diagnosisEntity = new DiagnosisEntity();
        diagnosisEntity.setPatient(petEntity).setDoctorName(doctorEntity.getFullName()).setDiagnoseDate(LocalDate.now())
                .setMedications(Set.of("med1", "med2")).setDoctorCommentary("test comment");

        diagnosisRepository.save(diagnosisEntity);
        this.testDiagnosisId = diagnosisEntity.getId();


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

    public long getTestDiagnosisId() {
        return testDiagnosisId;
    }
}
