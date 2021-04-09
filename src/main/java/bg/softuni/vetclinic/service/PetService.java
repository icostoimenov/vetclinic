package bg.softuni.vetclinic.service;

import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.PetServiceModel;
import bg.softuni.vetclinic.model.view.PetViewModel;

import java.io.IOException;
import java.util.List;

public interface PetService {
    void addPet(PetServiceModel serviceModel) throws IOException;
    List<PetViewModel> findPetsByOwner(UserEntity owner);

//    void addMedicalHistory(Long petId, String diagnose);

    PetEntity findById(Long id);

    PetEntity findByNameAndOwner(String petName, String ownerEmail);
}
