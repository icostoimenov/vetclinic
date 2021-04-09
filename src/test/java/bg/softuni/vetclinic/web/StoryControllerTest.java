package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.entities.StoryEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.repositories.StoryRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class StoryControllerTest {
    private static final String STORY_CONTROLLER_PREFIX = "/stories";

    private long testStoryId;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;


    @BeforeEach
    public void setUp() {
        this.init();
    }

    private void init() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@abv.bg").setPassword("123456").setFullName("Test Dummy").setPhoneNumber(8878548887985L);

        UserRoleEntity roleUser = new UserRoleEntity();
        roleUser.setRole(UserRole.USER);
        userRoleRepository.save(roleUser);

        userEntity.setRoles(List.of(roleUser));
        userRepository.save(userEntity);

        StoryEntity storyEntity = new StoryEntity();
        storyEntity.setTitle("Test Story Title").setStoryText("Test input text of the story").setCreatedOn(LocalDate.parse("2021-04-07"))
                .setImageUrl("https://res.cloudinary.com/dhgxopu1y/image/upload/v1617787028/tlsuf52uhgpgludnsfrw.jpg").setAuthor(userEntity);  //Passes empty image url to service :(

        storyRepository.save(storyEntity);
        testStoryId = storyEntity.getId();

    }

    @AfterEach
    public void tearDown() {
        storyRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();

    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "ADMIN", "DOCTOR"})
    void testShouldReturnValidStatusViewNameAndModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(STORY_CONTROLLER_PREFIX + "/{id}", testStoryId))
                .andExpect(status().isOk())
                .andExpect(view().name("view-story"))
                .andExpect(model().attributeExists("story"));
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "ADMIN", "DOCTOR"})
    void testViewingStoryShouldIncreaseViewsCounterByOne() throws Exception {
        Assertions.assertEquals(0, storyRepository.findById(testStoryId).orElseThrow().getViews());
        mockMvc.perform(MockMvcRequestBuilders.get(STORY_CONTROLLER_PREFIX + "/{id}", testStoryId));
        Assertions.assertEquals(1, storyRepository.findById(testStoryId).orElseThrow().getViews());
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "ADMIN", "DOCTOR"})
    void showAllStoriesShouldReturnValidStatusViewName() throws Exception {
        StoryEntity storyEntity = new StoryEntity();
        storyEntity.setTitle("Second Test Story Title").setStoryText("Second Test input text of the story").setCreatedOn(LocalDate.parse("2021-04-07"))
                .setImageUrl("https://res.cloudinary.com/dhgxopu1y/image/upload/v1617787028/tlsuf52uhgpgludnsfrw.jpg") //Passes empty image url to service :(
                .setAuthor(userRepository.findByEmail("test@abv.bg").orElseThrow(IllegalArgumentException::new));

        storyRepository.save(storyEntity);

        mockMvc.perform(MockMvcRequestBuilders.get(STORY_CONTROLLER_PREFIX + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("stories"))
                .andExpect(model().size(3));
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "ADMIN", "DOCTOR"})
    void addStory() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(STORY_CONTROLLER_PREFIX + "/add")
                .param("title", "test story test")
                .param("storyText", "test story text")
                .with(csrf())).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(2, storyRepository.count());
    }




}
