package bg.softuni.vetclinic.service;

import java.io.ByteArrayInputStream;

public interface CSVService {
    ByteArrayInputStream exportMedicalHistory(Long id);
}
