package bg.softuni.vetclinic.service.impl;

import static org.mockito.Mockito.when;

import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.repositories.UserRoleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl serviceToTest;
    private UserEntity testUser1, testUser2;

    @Mock
    UserRepository mockUserRepository;
    @Mock
    UserRoleRepository mockUserRoleRepository;
    @Mock
    PasswordEncoder mockPasswordEncoder;
    @Mock
    VetClinicUserService mockVetClinicUserService;

    @BeforeEach
    public void init() {
        testUser1 = new UserEntity();
        testUser1.setEmail("test@abv.bg").setPassword("123456").setFullName("Test Dummy").setPhoneNumber(8878548887985L);
        testUser1.setRoles(List.of(new UserRoleEntity().setRole(UserRole.USER)));



        serviceToTest = new UserServiceImpl(mockUserRoleRepository, mockUserRepository, mockPasswordEncoder, new ModelMapper(), mockVetClinicUserService);

    }

    @Test
    void testEmailExists(){
        when(mockUserRepository.findByEmail("test@abv.bg")).thenReturn(Optional.of(testUser1));

        Assertions.assertTrue(serviceToTest.emailExists("test@abv.bg"));

    }
}
