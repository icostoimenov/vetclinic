package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.PetServiceModel;
import bg.softuni.vetclinic.model.view.PetViewModel;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.service.CloudinaryService;
import bg.softuni.vetclinic.service.PetService;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;


    public PetServiceImpl(PetRepository petRepository, UserRepository userRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }


    @Override
    public void addPet(PetServiceModel serviceModel) throws IOException {
        if (serviceModel.getImageUrl().isEmpty()) {
            setDefaultImage(serviceModel);
        }
        MultipartFile img = serviceModel.getImageUrl();
        String imageUrl = cloudinaryService.uploadImage(img);

        PetEntity petEntity = modelMapper.map(serviceModel, PetEntity.class);
        UserEntity owner = userRepository.findByEmail(serviceModel.getOwner())
                .orElseThrow(() -> new IllegalArgumentException("Creator" + serviceModel.getOwner() + "could not be found"));
        petEntity.setOwner(owner);
        petEntity.setImageUrl(imageUrl);

        petRepository.save(petEntity);
    }

    @Override
    public List<PetViewModel> findPetsByOwner(UserEntity owner) {
        return petRepository.findAllByOwner(owner).stream().map(pe -> modelMapper.map(pe, PetViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void addMedicalHistory(Long petId, String diagnose) {
        PetEntity pet = petRepository.findById(petId).orElseThrow(IllegalArgumentException::new);
        pet.getMedicalHistory().add(diagnose);
    }

    @Override
    public PetEntity findById(Long id) {
        return petRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    private void setDefaultImage(PetServiceModel petServiceModel) throws IOException {
        File file;
        MultipartFile result;
        FileInputStream input;
        switch (petServiceModel.getType().name()) {
            case "DOG":
                file = new File("src/main/resources/static/img/defaults/dog-icon.jpg");
                input = new FileInputStream(file);
                result = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input));
                petServiceModel.setImageUrl(result);
                break;
            case "CAT":
                file = new File("src/main/resources/static/img/defaults/cat-icon.jpg");
                input = new FileInputStream(file);
                result = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input));
                petServiceModel.setImageUrl(result);
                break;
            case "RABBIT":
                file = new File("src/main/resources/static/img/defaults/rabbit-icon.jpg");
                input = new FileInputStream(file);
                result = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input));
                petServiceModel.setImageUrl(result);
                break;
            case "BIRD":
                file = new File("src/main/resources/static/img/defaults/bird-icon.jpg");
                input = new FileInputStream(file);
                result = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input));
                petServiceModel.setImageUrl(result);
                break;
            case "REPTILE":
                file = new File("src/main/resources/static/img/defaults/reptile-icon.jpg");
                input = new FileInputStream(file);
                result = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input));
                petServiceModel.setImageUrl(result);
                break;
            default:
                file = new File("src/main/resources/static/img/defaults/other-icon.jpg");
                input = new FileInputStream(file);
                result = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input));
                petServiceModel.setImageUrl(result);
                break;
        }

    }
}

