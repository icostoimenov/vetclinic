package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.binding.AppointmentAddBindingModel;
import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.AppointmentServiceModel;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.repositories.UserRoleRepository;
import bg.softuni.vetclinic.service.AppointmentService;
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
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/appointments")

public class AppointmentController {

    private final UserService userService;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;

    public AppointmentController(UserService userService, PetRepository petRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, AppointmentService appointmentService) {
        this.userService = userService;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.appointmentService = appointmentService;
    }

    @ModelAttribute("appointmentAddBindingModel")
    public AppointmentAddBindingModel createBindingModel() {
        return new AppointmentAddBindingModel();
    }

    @GetMapping("/make")
    public String makeAnAppointment(@AuthenticationPrincipal UserDetails principal, Model model) {
        UserEntity currentUser = userService.findByEmail(principal.getUsername());
        List<PetEntity> pets = petRepository.findAllByOwner(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("pets", pets);
        model.addAttribute("doctors", userRepository.findDoctorsOnly());
        return "make-appointment";
    }

    @PostMapping("/make")
    public String makeAnAppointment(@Valid AppointmentAddBindingModel appointmentAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal UserDetails principal, @RequestParam Long docId, @RequestParam Long petId)  {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("appointmentAddBindingModel", appointmentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.appointmentAddBindingModel", bindingResult);

            return "redirect:make";
        }

        AppointmentServiceModel appointmentServiceModel = modelMapper.map(appointmentAddBindingModel, AppointmentServiceModel.class);
        appointmentServiceModel.setCreator(principal.getUsername());
        appointmentServiceModel.setDoctorId(docId);
        appointmentServiceModel.setPetId(petId);

//        appointmentServiceModel.setAppointmentDate(appointmentAddBindingModel.getAppointmentDate());

        appointmentService.addAppointment(appointmentServiceModel);

        return "redirect:/home";

    }


}
