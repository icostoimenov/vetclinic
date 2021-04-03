package bg.softuni.vetclinic.service;

import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.PetServiceModel;

import java.io.IOException;
import java.util.List;

public interface PetService {
    void addPet(PetServiceModel serviceModel) throws IOException;
    List<PetEntity> findPetsByOwner(UserEntity owner);
}
