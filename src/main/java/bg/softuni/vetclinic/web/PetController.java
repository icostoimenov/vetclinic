package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.binding.PetAddBindingModel;
import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.PetServiceModel;
import bg.softuni.vetclinic.service.PetService;
import bg.softuni.vetclinic.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/pets")
public class PetController {

    private final ModelMapper modelMapper;
    private final PetService petService;
    private final UserService userService;

    public PetController(ModelMapper modelMapper, PetService petService, UserService userService) {
        this.modelMapper = modelMapper;
        this.petService = petService;
        this.userService = userService;
    }

    @ModelAttribute("petAddBindingModel")
    public PetAddBindingModel createBindingModel(){
        return new PetAddBindingModel();
    }


    @GetMapping("/my-pets")
    public String showPets(@AuthenticationPrincipal UserDetails principal, Model model) {
        UserEntity currentUser = userService.findByEmail(principal.getUsername());
        model.addAttribute("pets", petService.findPetsByOwner(currentUser));

        return "my-pets";
    }

    @GetMapping("/add-pet")
    public String addNewPet(){
        return "add-pet";
    }

    @PostMapping("/add-pet")
    public String addNewPet(@Valid PetAddBindingModel petAddBindingModel, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("petAddBindingModel", petAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.petAddBindingModel", bindingResult);

            return "redirect:add-pet";
        }

        PetServiceModel petServiceModel = modelMapper.map(petAddBindingModel, PetServiceModel.class);
        petServiceModel.setOwner(principal.getUsername());

        if(petServiceModel.getImageUrl().trim().isEmpty()){
            setDefaultImage(petServiceModel);
        }


        petService.addPet(petServiceModel);

        return "redirect:/pets/my-pets";
    }

    private void setDefaultImage(PetServiceModel petServiceModel) {
        switch (petServiceModel.getType().name()) {
            case "DOG":
                petServiceModel.setImageUrl("../img/defaults/dog-icon.jpg");
                break;
            case "CAT":
                petServiceModel.setImageUrl("../img/defaults/cat-icon.jpg");
                break;
            case "RABBIT":
                petServiceModel.setImageUrl("../img/defaults/rabbit-icon.jpg");
                break;
            case "BIRD":
                petServiceModel.setImageUrl("../img/defaults/bird-icon.jpg");
                break;
            case "REPTILE":
                petServiceModel.setImageUrl("../img/defaults/reptile-icon.jpg");
                break;
            default:
                petServiceModel.setImageUrl("../img/defaults/other-icon.jpg");
                break;
        }

    }

}
