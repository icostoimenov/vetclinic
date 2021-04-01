package bg.softuni.vetclinic.repositories;

import bg.softuni.vetclinic.model.entities.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {

}
