package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
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
    @Autowired
    DiagnosisRepository diagnosisRepository;


    private AppointmentTestData testData;
    private long testAppointmentId;

    @BeforeEach
    public void setUp(){
        testData = new AppointmentTestData(userRepository, petRepository, appointmentRepository, userRoleRepository, diagnosisRepository);
        testData.init();
        testAppointmentId = testData.getTestAppointmentId();
    }
//    @AfterEach
//    public void tearDown(){
//        this.testData.cleanUp();
//    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "DOCTOR"})
    public void testFetchAppointments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/appointments/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].appointmentDate").value(String.valueOf(LocalDate.now())));
    }
}
