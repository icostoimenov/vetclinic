package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.StoryEntity;
import bg.softuni.vetclinic.repositories.StoryRepository;
import bg.softuni.vetclinic.service.TopStoryService;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;

@Service
public class TopStoryServiceImpl implements TopStoryService {

    private final StoryRepository storyRepository;
    private StoryEntity topStory;

    private Logger LOGGER = LoggerFactory.getLogger(TopStoryServiceImpl.class);


    public TopStoryServiceImpl( StoryRepository storyRepository){
        this.storyRepository = storyRepository;
        this.topStory = storyRepository.findFirstByOrderByViewsDesc();
    }



    public StoryEntity getTopStory(){
        return storyRepository.findFirstByOrderByViewsDesc();
    }

    @Override
    public StoryEntity topStory() {
        return this.topStory;
    }

    @Scheduled(cron = "${topStoryCron.refresh-cron}")
    public void refreshStories(){
        LOGGER.info("Refreshing stories...");
        this.topStory = getTopStory();
    }
}
