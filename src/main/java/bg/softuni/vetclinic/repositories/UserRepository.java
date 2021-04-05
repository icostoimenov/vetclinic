package bg.softuni.vetclinic.repositories;

import bg.softuni.vetclinic.model.entities.DoctorEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);


    Optional<DoctorEntity> findDoctorById(Long id);

    @Query("SELECT d FROM DoctorEntity d" + " WHERE size(d.roles) = 2 ")
    List<UserEntity> findDoctorsOnly();




}
