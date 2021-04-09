package bg.softuni.vetclinic.repositories;

import bg.softuni.vetclinic.model.entities.AppointmentEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findAllByStatusLikeOrderByAppointmentDateAsc(AppointmentStatus appointmentStatus);

    List<AppointmentEntity> findAllByDoctorAndStatusLikeOrderByAppointmentDateAsc(UserEntity doctorEntity, AppointmentStatus appointmentStatus);

    @Modifying
    @Query("UPDATE AppointmentEntity ap set ap.status = ?1 where ap.id = ?2")
    void updateAppDiagnoseAndStatus(AppointmentStatus appointmentStatus, Long appId);
}
