package bg.softuni.vetclinic.service.impl;

import static org.mockito.Mockito.when;

import bg.softuni.vetclinic.model.entities.PetEntity;
import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.PetType;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.model.view.PetViewModel;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.service.CloudinaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PetServiceImplTest {

    private PetServiceImpl serviceToTest;
    private UserEntity testUser1;
    private PetEntity testPet1, testPet2;

    @Mock
    UserRepository mockUserRepository;
    @Mock
    PetRepository mockPetRepository;
    @Mock
    CloudinaryService mockCloudinaryService;

    @BeforeEach
    public void init() {
        testUser1 = new UserEntity();
        testUser1.setEmail("test@abv.bg").setPassword("123456").setFullName("Test Dummy").setPhoneNumber(8878548887985L);
        testUser1.setRoles(List.of(new UserRoleEntity().setRole(UserRole.USER)));

        testPet1 = new PetEntity();
        testPet1.setOwner(testUser1).setImageUrl("test").setGender("MALE").setAge(3).setType(PetType.CAT).setName("TestPet");
        testPet2 = new PetEntity();
        testPet2.setOwner(testUser1).setImageUrl("test").setGender("FEMALE").setAge(4).setType(PetType.DOG).setName("TestPet2");

        serviceToTest = new PetServiceImpl(mockPetRepository, mockUserRepository, new ModelMapper(),  mockCloudinaryService);

    }

    @Test
    public void testFindPetsByOwner(){
        when(mockPetRepository.findAllByOwner(testUser1)).thenReturn(List.of(testPet1, testPet2));

        List<PetViewModel> pets = serviceToTest.findPetsByOwner(testUser1);

        Assertions.assertEquals(2, pets.size());

        PetViewModel model1 = pets.get(0);
        PetViewModel model2 = pets.get(1);

        Assertions.assertEquals(testPet1.getName(), model1.getName());
        Assertions.assertEquals(testPet1.getType(), model1.getType());
        Assertions.assertEquals(testPet1.getImageUrl(), model1.getImageUrl());
        Assertions.assertEquals(testPet1.getAge(), model1.getAge());
        Assertions.assertEquals(testPet1.getGender(), model1.getGender());

        Assertions.assertEquals(testPet2.getName(), model2.getName());
        Assertions.assertEquals(testPet2.getType(), model2.getType());
        Assertions.assertEquals(testPet2.getImageUrl(), model2.getImageUrl());
        Assertions.assertEquals(testPet2.getAge(), model2.getAge());
        Assertions.assertEquals(testPet2.getGender(), model2.getGender());
    }
}
