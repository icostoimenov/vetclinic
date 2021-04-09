package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.helper.CSVHelper;
import bg.softuni.vetclinic.model.entities.DiagnosisEntity;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.service.CSVService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Set;

@Service
public class CSVServiceImpl implements CSVService {
    private final PetRepository petRepository;

    public CSVServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    @Override
    public ByteArrayInputStream exportMedicalHistory(Long id) {
        Set<DiagnosisEntity> history = petRepository.findById(id).orElseThrow(IllegalArgumentException::new).getMedicalHistory();
        return CSVHelper.historyToCSV(history);
    }
}
