package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import bg.softuni.vetclinic.model.view.AppointmentViewModel;
import bg.softuni.vetclinic.repositories.AppointmentRepository;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public AppointmentRestController(AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public ResponseEntity<List<AppointmentViewModel>> findAllPendingAppointmentDates() {
        List<AppointmentViewModel> dates = appointmentRepository.findAllByStatusLikeOrderByAppointmentDateAsc(AppointmentStatus.PENDING).stream().map(ae ->
                modelMapper.map(ae, AppointmentViewModel.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(dates);
    }
}
