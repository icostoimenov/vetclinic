package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.binding.AppointmentAddBindingModel;
import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import bg.softuni.vetclinic.model.service.AppointmentServiceModel;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.service.AppointmentService;
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

@Controller
@RequestMapping("/appointments")

public class AppointmentController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;
    private final PetRepository petRepository;
    private final PetService petService;

    public AppointmentController(UserService userService, ModelMapper modelMapper, AppointmentService appointmentService, PetRepository petRepository, PetService petService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.appointmentService = appointmentService;
        this.petRepository = petRepository;
        this.petService = petService;
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
        model.addAttribute("doctors", userService.findDoctorsOnly());
        return "make-appointment";
    }

    @PostMapping("/make")
    public String makeAnAppointment(@Valid AppointmentAddBindingModel appointmentAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal UserDetails principal, @RequestParam(required = false) Long docId, @RequestParam(required = false) Long petId) {

        if (bindingResult.hasErrors() || docId == null || petId == null) {
            redirectAttributes.addFlashAttribute("appointmentAddBindingModel", appointmentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.appointmentAddBindingModel", bindingResult);

            return "redirect:make";
        }

        AppointmentServiceModel appointmentServiceModel = modelMapper.map(appointmentAddBindingModel, AppointmentServiceModel.class);
        appointmentServiceModel.setCreator(principal.getUsername());
        appointmentServiceModel.setDoctorId(docId);
        appointmentServiceModel.setPetId(petId);

        //todo appointment error handling

        appointmentService.addAppointment(appointmentServiceModel);

        return "redirect:/home";

    }

    @GetMapping("/pending")
    public String pendingApps(@AuthenticationPrincipal UserDetails principal, Model model){
        UserEntity currentUser = userService.findByEmail(principal.getUsername());
        List<AppointmentEntity> pendingApps = appointmentService.allActiveAppsForDoctor(currentUser, AppointmentStatus.PENDING);
        model.addAttribute("pendingAppointments", pendingApps);

        return "appointments";
    }

    @PutMapping("/diagnose/{id}")
    public String diagnosePatient(@PathVariable Long id, @RequestParam("petId") Long petId, @RequestParam("diagnose") String diagnose, RedirectAttributes redirectAttributes){
        AppointmentStatus status = AppointmentStatus.FINISHED;

        appointmentService.setDiagnose(diagnose, status, id);
        petService.addMedicalHistory(petId, diagnose);

        return "redirect:/appointments/pending";
    }

//    @GetMapping("/diagnose/*")
//    public String diagnoseRedirect(){
//        return "redirect:/appointments/pending";
//    }





}
