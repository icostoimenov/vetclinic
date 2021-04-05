package bg.softuni.vetclinic.repositories;

import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findAllByOwner(UserEntity owner);
    Optional<PetEntity> findById(Long id);
}
