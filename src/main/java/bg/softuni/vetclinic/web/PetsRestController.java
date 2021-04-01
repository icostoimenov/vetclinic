package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.view.PetViewModel;
import bg.softuni.vetclinic.repositories.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/pets")
@RestController
public class PetsRestController {

    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    public PetsRestController(PetRepository petRepository, ModelMapper modelMapper) {
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/api")
    public List<PetViewModel> findAll() {
        return petRepository.findAll().stream().map(pe -> modelMapper.map(pe, PetViewModel.class)).collect(Collectors.toList());
    }
}
