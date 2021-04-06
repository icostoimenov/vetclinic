package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.binding.PetAddBindingModel;
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
import java.io.IOException;

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
    public PetAddBindingModel createBindingModel() {
        return new PetAddBindingModel();
    }


    @GetMapping("/my-pets")
    public String showPets(@AuthenticationPrincipal UserDetails principal, Model model) {
        UserEntity currentUser = userService.findByEmail(principal.getUsername());
        model.addAttribute("pets", petService.findPetsByOwner(currentUser));

        return "my-pets";
    }

    @GetMapping("/add-pet")
    public String addNewPet() {
        return "add-pet";
    }

    @PostMapping("/add-pet")
    public String addNewPet(@Valid PetAddBindingModel petAddBindingModel, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("petAddBindingModel", petAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.petAddBindingModel", bindingResult);

            return "redirect:add-pet";
        }

        PetServiceModel petServiceModel = modelMapper.map(petAddBindingModel, PetServiceModel.class);
        petServiceModel.setOwner(principal.getUsername());


        petService.addPet(petServiceModel);

        return "redirect:/pets/my-pets";
    }

    @GetMapping("/{id}")
    public String petPreview(@PathVariable Long id){

        return "inner-page";
    }


}
