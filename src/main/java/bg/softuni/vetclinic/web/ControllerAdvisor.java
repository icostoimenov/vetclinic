package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import bg.softuni.vetclinic.service.AppointmentService;
import bg.softuni.vetclinic.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class ControllerAdvisor {

    private final UserService userService;
    private final AppointmentService appointmentService;

    public ControllerAdvisor(UserService userService, AppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @ModelAttribute("notifications")
    public int notifications(@AuthenticationPrincipal UserDetails principal) {
        if (principal != null) {
            if (principal.getAuthorities().size() == 2) {
                UserEntity currentUser = userService.findByEmail(principal.getUsername());
                List<AppointmentEntity> pendingApps = appointmentService.allActiveAppsForDoctor(currentUser, AppointmentStatus.PENDING);
                return pendingApps.size();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
