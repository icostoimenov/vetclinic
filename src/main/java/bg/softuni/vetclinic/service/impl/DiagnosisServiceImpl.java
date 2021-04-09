package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.DiagnosisEntity;
import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.DiagnosisServiceModel;
import bg.softuni.vetclinic.repositories.DiagnosisRepository;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.service.DiagnosisService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository, UserRepository userRepository, PetRepository petRepository, ModelMapper modelMapper) {
        this.diagnosisRepository = diagnosisRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addDiagnosis(DiagnosisServiceModel diagnosisServiceModel) {

        DiagnosisEntity diagnosisEntity = modelMapper.map(diagnosisServiceModel, DiagnosisEntity.class);

        UserEntity doctorEntity = userRepository.findByEmail(diagnosisServiceModel.getDoctorEmail())
        .orElseThrow(() -> new IllegalArgumentException("Doctor with email " + diagnosisServiceModel.getDoctorEmail() + " could not be found"));

        PetEntity patient = petRepository.findById(diagnosisServiceModel.getPatient()).orElseThrow(IllegalAccessError::new);

        diagnosisEntity.setDoctorName(doctorEntity.getFullName());
        diagnosisEntity.setPatient(patient);

        diagnosisRepository.save(diagnosisEntity);

        patient.getMedicalHistory().add(diagnosisEntity);

    }
}
