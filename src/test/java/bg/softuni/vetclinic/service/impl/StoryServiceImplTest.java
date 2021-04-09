package bg.softuni.vetclinic.service.impl;

import static org.mockito.Mockito.when;
import bg.softuni.vetclinic.model.entities.StoryEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.repositories.StoryRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.service.StoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StoryServiceImplTest {

    private StoryService serviceToTest;
    private StoryEntity testStory1;
    private StoryEntity testStory2;
    private UserEntity testAuthor;

    @Mock
    StoryRepository mockStoryRepository;
    @Mock
    UserRepository mockUserRepository;
    @Mock
    CloudinaryServiceImpl mockCloudinaryService;

    @BeforeEach
    public void init() {
        testAuthor = new UserEntity();
        testAuthor.setEmail("test@abv.bg").setPassword("123456").setFullName("Test Dummy").setPhoneNumber(8878548887985L);
        testAuthor.setRoles(List.of(new UserRoleEntity().setRole(UserRole.USER)));

        testStory1= new StoryEntity();
        testStory2= new StoryEntity();
        testStory1.setTitle("Test Story 1").setAuthor(testAuthor).setImageUrl("testImgUrl").setStoryText("test story text").setCreatedOn(LocalDate.now()).setViews(0);
        testStory2.setTitle("Test Story 2").setAuthor(testAuthor).setImageUrl("testImgUrl2").setStoryText("test story text2").setCreatedOn(LocalDate.now()).setViews(0);

        serviceToTest = new StoryServiceImpl(mockStoryRepository, mockUserRepository, mockCloudinaryService, new ModelMapper()) {
        };

    }

    @Test
    public void testFindAllStories(){
        when(mockStoryRepository.findAll()).thenReturn(List.of(testStory1, testStory2));

        List<StoryEntity> allStories = serviceToTest.findAllStories();

        Assertions.assertEquals(2, allStories.size());
    }


}
