package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.repositories.AppointmentRepository;
import bg.softuni.vetclinic.repositories.PetRepository;
import bg.softuni.vetclinic.repositories.UserRepository;
import bg.softuni.vetclinic.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AppointmentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;


    private AppointmentTestData testData;
    private long testAppointmentId;

    @BeforeEach
    public void setUp(){
        testData = new AppointmentTestData(userRepository, petRepository, appointmentRepository, userRoleRepository);
        testData.init();
        testAppointmentId = testData.getTestAppointmentId();
    }
    @AfterEach
    public void tearDown(){
        this.testData.cleanUp();
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "DOCTOR"})
    public void testFetchAlbums() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/appointments/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].appointmentDate").value("2021-04-08"));
    }
}
