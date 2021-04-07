package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.StoryEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.service.StoryServiceModel;
import bg.softuni.vetclinic.repositories.StoryRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.service.CloudinaryService;
import bg.softuni.vetclinic.service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public StoryServiceImpl(StoryRepository storyRepository, UserRepository userRepository, CloudinaryServiceImpl cloudinaryService, ModelMapper modelMapper) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void postStory(StoryServiceModel storyServiceModel) throws IOException {
        MultipartFile img = storyServiceModel.getImageUrl();
        String imageUrl = cloudinaryService.uploadImage(img);

        StoryEntity storyEntity = modelMapper.map(storyServiceModel, StoryEntity.class);
        storyEntity.setCreatedOn(LocalDate.now());
        storyEntity.setImageUrl(imageUrl);
        storyEntity.setViews(0);

        UserEntity creator = userRepository.findByEmail(storyServiceModel.getAuthor())
                .orElseThrow(() -> new IllegalArgumentException("Creator" + storyServiceModel.getAuthor() + " could not be found"));
        storyEntity.setAuthor(creator);

        storyRepository.save(storyEntity);
    }

    @Override
    public List<StoryEntity> findAllStories() {
        return storyRepository.findAll();
    }

    @Override
    public StoryEntity findStoryById(Long id) {
        return storyRepository.findById(id).orElseThrow(() -> new IllegalStateException("Story not found"));
    }

    @Override
    public void increaseViews(Long id) {
        storyRepository.updateViewsCounter(id);
    }
}
