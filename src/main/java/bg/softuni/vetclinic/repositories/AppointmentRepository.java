package bg.softuni.vetclinic.repositories;

import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findAllByStatusLike(AppointmentStatus appointmentStatus);
}
