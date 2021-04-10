package bg.softuni.vetclinic.repositories;

import bg.softuni.vetclinic.model.entities.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {

    StoryEntity findFirstByOrderByViewsDesc();

    @Modifying
    @Query("UPDATE StoryEntity story set story.views = story.views + 1 where story.id =?1")
    void updateViewsCounter(Long id);
}
