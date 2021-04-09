package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.binding.DoctorRegistrationBindingModel;
import bg.softuni.vetclinic.model.entities.DoctorEntity;
import bg.softuni.vetclinic.model.service.DoctorRegistrationServiceModel;
import bg.softuni.vetclinic.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public DoctorController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ModelAttribute("registrationBindingModel")
    public DoctorRegistrationBindingModel createBindingModel(){
        return new DoctorRegistrationBindingModel();
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("doctor", new DoctorEntity());
        return "doc-register";
    }

    @PostMapping("/register")
    public String registerAndLoginUser(@Valid DoctorRegistrationBindingModel registrationBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registrationBindingModel", registrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationBindingModel", bindingResult);

            return "redirect:/doctors/register";
        }

        if(userService.emailExists(registrationBindingModel.getEmail())){
            redirectAttributes.addFlashAttribute("registrationBindingModel", registrationBindingModel);
            redirectAttributes.addFlashAttribute("doctorExistsError", true);

            return "redirect:/doctors/register";
        }

        DoctorRegistrationServiceModel  doctorService = modelMapper.map(registrationBindingModel, DoctorRegistrationServiceModel.class);
        userService.registerDoctor(doctorService);

        return "redirect:/home";

    }


}
