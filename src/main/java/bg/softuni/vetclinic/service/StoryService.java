package bg.softuni.vetclinic.service;

import bg.softuni.vetclinic.model.entities.StoryEntity;
import bg.softuni.vetclinic.model.service.StoryServiceModel;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Transactional
public interface StoryService {

    void postStory(StoryServiceModel storyServiceModel) throws IOException;
    List<StoryEntity> findAllStories();
    StoryEntity findStoryById(Long id);


    void increaseViews(Long id);
}
