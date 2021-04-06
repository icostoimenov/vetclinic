package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import bg.softuni.vetclinic.repositories.AppointmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/appointments")
@RestController
public class AppointmentRestController {
    private final AppointmentRepository appointmentRepository;

    public AppointmentRestController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/api")
    public ResponseEntity<List<String>> findAllPendingAppointmentDates() {
        List<String> dates = appointmentRepository.findAllByStatusLikeOrderByAppointmentDateAsc(AppointmentStatus.PENDING).stream().map(ae -> ae.getAppointmentDate().toString()).collect(Collectors.toList());
        return ResponseEntity.ok().body(dates);
    }
}
