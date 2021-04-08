package bg.softuni.vetclinic.service.impl;

import bg.softuni.vetclinic.model.entities.UserEntity;
import bg.softuni.vetclinic.model.entities.UserRoleEntity;
import bg.softuni.vetclinic.model.entities.enums.UserRole;
import bg.softuni.vetclinic.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class VetClinicUserServiceTest {

    private VetClinicUserService serviceToTest;

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new VetClinicUserService(mockUserRepository);
    }

    @Test
    public void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            serviceToTest.loadUserByUsername("non_existent_user");
        });
    }

    @Test
    void testExistingUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@abv.bg").setPassword("123456").setFullName("Test Dummy").setPhoneNumber(8878548887985L);

        UserRoleEntity roleUser = new UserRoleEntity();
        UserRoleEntity roleAdmin = new UserRoleEntity();
        UserRoleEntity roleDoctor = new UserRoleEntity();
        roleUser.setRole(UserRole.USER);
        roleDoctor.setRole(UserRole.DOCTOR);
        roleAdmin.setRole(UserRole.ADMIN);

        userEntity.setRoles(List.of(roleUser, roleAdmin, roleDoctor));

        Mockito.when(mockUserRepository.findByEmail("test@abv.bg")).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = serviceToTest.loadUserByUsername("test@abv.bg");
        Assertions.assertEquals(userEntity.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(3, userDetails.getAuthorities().size());
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorities.contains("ROLE_USER"));
        Assertions.assertTrue(authorities.contains("ROLE_DOCTOR"));
    }
}
