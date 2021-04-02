package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.PetServiceModel;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PetServiceImpl(PetRepository petRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addPet(PetServiceModel serviceModel) {
        PetEntity petEntity = modelMapper.map(serviceModel, PetEntity.class);
        UserEntity owner = userRepository.findByEmail(serviceModel.getOwner())
                .orElseThrow(() -> new IllegalArgumentException("Creator" + serviceModel.getOwner() + "could not be found"));
        petEntity.setOwner(owner);

        petRepository.save(petEntity);
    }

    @Override
    public List<PetEntity> findPetsByOwner(UserEntity owner) {
        return petRepository.findAllByOwner(owner);
    }
}
