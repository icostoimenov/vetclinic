package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.StoryEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.PetType;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.repositories.PetRepository;
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
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PetControllerTest {

    private static final String PET_CONTROLLER_PREFIX = "/pets";


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @BeforeEach
    private void init() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@abv.bg").setPassword("123456").setFullName("Test Dummy").setPhoneNumber(8878548887985L);

        UserRoleEntity roleUser = new UserRoleEntity();
        roleUser.setRole(UserRole.USER);
        userRoleRepository.save(roleUser);

        userEntity.setRoles(List.of(roleUser));
        userRepository.save(userEntity);

        PetEntity petEntity = new PetEntity();
        petEntity.setName("TestPet").setAge(3).setGender("MALE").setType(PetType.CAT).setImageUrl("testimg").setOwner(userEntity);

        petRepository.save(petEntity);

    }
    @AfterEach
    private void cleanUp(){
        petRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "ADMIN", "DOCTOR"})
    void testShouldReturnValidStatusViewNameAndModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PET_CONTROLLER_PREFIX + "/add-pet"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-pet"));
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "ADMIN", "DOCTOR"})
    void addPetAndDefaultImageSet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PET_CONTROLLER_PREFIX + "/add-pet")
                .param("name", "TestingPet")
                .param("age", "4")
                .param("gender", "MALE")
                .param("type", PetType.DOG.name())
                .with(csrf())).andExpect(status().is3xxRedirection());
        mockMvc.perform(MockMvcRequestBuilders.post(PET_CONTROLLER_PREFIX + "/add-pet")
                .param("name", "TestingPet")
                .param("age", "4")
                .param("gender", "MALE")
                .param("type", PetType.CAT.name())
                .with(csrf())).andExpect(status().is3xxRedirection());
        mockMvc.perform(MockMvcRequestBuilders.post(PET_CONTROLLER_PREFIX + "/add-pet")
                .param("name", "TestingPet")
                .param("age", "4")
                .param("gender", "MALE")
                .param("type", PetType.BIRD.name())
                .with(csrf())).andExpect(status().is3xxRedirection());
        mockMvc.perform(MockMvcRequestBuilders.post(PET_CONTROLLER_PREFIX + "/add-pet")
                .param("name", "TestingPet")
                .param("age", "4")
                .param("gender", "MALE")
                .param("type", PetType.RABBIT.name())
                .with(csrf())).andExpect(status().is3xxRedirection());
        mockMvc.perform(MockMvcRequestBuilders.post(PET_CONTROLLER_PREFIX + "/add-pet")
                .param("name", "TestingPet")
                .param("age", "4")
                .param("gender", "MALE")
                .param("type", PetType.REPTILE.name())
                .with(csrf())).andExpect(status().is3xxRedirection());
        mockMvc.perform(MockMvcRequestBuilders.post(PET_CONTROLLER_PREFIX + "/add-pet")
                .param("name", "TestingPet")
                .param("age", "4")
                .param("gender", "MALE")
                .param("type", PetType.OTHER.name())
                .with(csrf())).andExpect(status().is3xxRedirection());


        Assertions.assertEquals(7, petRepository.count());
    }


}
