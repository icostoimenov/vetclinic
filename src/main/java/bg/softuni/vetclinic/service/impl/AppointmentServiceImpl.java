package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.AppointmentServiceModel;
import bg.softuni.vetclinic.repositories.AppointmentRepository;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final ModelMapper modelMapper;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public AppointmentServiceImpl(ModelMapper modelMapper, AppointmentRepository appointmentRepository, UserRepository userRepository, PetRepository petRepository) {
        this.modelMapper = modelMapper;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    @Override
    public void addAppointment(AppointmentServiceModel appointmentServiceModel) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentEntity appointment = modelMapper.map(appointmentServiceModel, AppointmentEntity.class);
        appointment.setAppointmentDate(appointmentServiceModel.getAppointmentDate());
        appointment.setDoctor(userRepository.findDoctorById(appointmentServiceModel.getDoctorId())
                .orElseThrow(IllegalArgumentException::new));
        appointment.setPet(petRepository.findById(appointmentServiceModel.getPetId()).orElseThrow(IllegalArgumentException::new));

        UserEntity creator = userRepository.findByEmail(appointmentServiceModel.getCreator())
                .orElseThrow(() -> new IllegalArgumentException("Creator" + appointment.getOwnerEmail() + " could not be found"));
        appointment.setCreator(creator);
        appointment.setOwnerName(creator.getFullName()).setOwnerEmail(creator.getEmail()).setOwnerPhone(creator.getPhoneNumber());

        appointmentRepository.save(appointment);
    }
}
