package bg.softuni.vetclinic.repositories;

import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
