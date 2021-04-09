package bg.softuni.vetclinic.repositories;

import bg.softuni.vetclinic.model.entities.DiagnosisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<DiagnosisEntity, Long> {

}
