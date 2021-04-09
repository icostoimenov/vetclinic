package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.binding.AppointmentAddBindingModel;
import bg.softuni.vetclinic.model.binding.DiagnosisAddBindingModel;
import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import bg.softuni.vetclinic.model.service.AppointmentServiceModel;
import bg.softuni.vetclinic.model.service.DiagnosisServiceModel;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.service.AppointmentService;
import bg.softuni.vetclinic.service.DiagnosisService;
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
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/appointments")

public class AppointmentController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;
    private final PetRepository petRepository;
    private final PetService petService;
    private final DiagnosisService diagnosisService;

    public AppointmentController(UserService userService, ModelMapper modelMapper, AppointmentService appointmentService, PetRepository petRepository, PetService petService, DiagnosisService diagnosisService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.appointmentService = appointmentService;
        this.petRepository = petRepository;
        this.petService = petService;
        this.diagnosisService = diagnosisService;
    }

    @ModelAttribute("appointmentAddBindingModel")
    public AppointmentAddBindingModel createBindingModel() {
        return new AppointmentAddBindingModel();
    }

    @ModelAttribute("diagnosisAddBindingModel")
    public DiagnosisAddBindingModel createDiagnosisBindingModel() {
        return new DiagnosisAddBindingModel();
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

        appointmentService.addAppointment(appointmentServiceModel);

        return "redirect:/home";

    }

    @GetMapping("/pending")
    public String pendingApps(@AuthenticationPrincipal UserDetails principal, Model model) {
        if (principal.getAuthorities().size() == 2) {
            UserEntity currentUser = userService.findByEmail(principal.getUsername());
            List<AppointmentEntity> pendingApps = appointmentService.allActiveAppsForDoctor(currentUser, AppointmentStatus.PENDING);
            model.addAttribute("pendingAppointments", pendingApps);
        }
        return "appointments";
    }

    @PostMapping("/diagnose/{id}")
    public String diagnosePatient(@PathVariable Long id, @Valid DiagnosisAddBindingModel diagnosisAddBindingModel, BindingResult bindingResult,
                                  @RequestParam("petId") Long petId, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("diagnosisAddBindingModel", diagnosisAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.diagnosisAddBindingModel", bindingResult);

            return "redirect:/appointments/pending";
        }

        DiagnosisServiceModel diagnosisServiceModel = modelMapper.map(diagnosisAddBindingModel, DiagnosisServiceModel.class);
        diagnosisServiceModel.setPatient(petId);
        diagnosisServiceModel.setDoctorEmail(principal.getUsername());
        diagnosisServiceModel.setDiagnoseDate(LocalDate.now());

        diagnosisService.addDiagnosis(diagnosisServiceModel);

        AppointmentStatus status = AppointmentStatus.FINISHED;
        appointmentService.setDiagnose(status, id);
//        petService.addMedicalHistory(petId, diagnose);
//
        return "redirect:/appointments/pending";
    }

}
