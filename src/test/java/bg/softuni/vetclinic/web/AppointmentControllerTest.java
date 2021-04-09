package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.repositories.*;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AppointmentControllerTest {
    private static final String APPOINTMENT_CONTROLLER_PREFIX = "/appointments";

    private long testPetId;
    private long testDoctorId;
    private long testDiagnosisId;

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

    @BeforeEach
    public void setUp() {
        testData = new AppointmentTestData(userRepository, petRepository, appointmentRepository, userRoleRepository, diagnosisRepository);
        testData.init();
        testPetId = testData.getTestPetId();
        testDoctorId = testData.getTestDoctorId();
        testDiagnosisId = testData.getTestDiagnosisId();
    }

//    @AfterEach
//    public void tearDown(){
//        testData.cleanUp();
//    }


    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "DOCTOR"})
    void testShouldReturnValidStatusViewNameAndModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(APPOINTMENT_CONTROLLER_PREFIX + "/make"))
                .andExpect(status().isOk())
                .andExpect(view().name("make-appointment"))
                .andExpect(model().attributeExists("currentUser"))
                .andExpect(model().attributeExists("pets"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "DOCTOR"})
    void addAppointment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(APPOINTMENT_CONTROLLER_PREFIX + "/make")
                .param("additionalInfo", "test story test")
                .param("appointmentDate", "04/07/2021")
                .param("petId", String.valueOf(this.testPetId))
                .param("docId", String.valueOf(this.testDoctorId))
                .with(csrf())).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(2, appointmentRepository.count());
    }
    @Test
    @WithMockUser(value = "test@abv.bg", roles = {"USER", "DOCTOR"})
    void diagnosePatient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(APPOINTMENT_CONTROLLER_PREFIX + "/diagnose/{id}", testDiagnosisId)
                .param("petId", String.valueOf(testPetId))
                .param("medications", "med1, med2")
                .param("doctorCommentary", "test comment")
                .with(csrf())).andExpect(status().is3xxRedirection());
        Assertions.assertEquals(2, diagnosisRepository.count());
    }

}
