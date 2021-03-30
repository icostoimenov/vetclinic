package bg.softuni.vetclinic;

import bg.softuni.vetclinic.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VetClinicApplicationInit implements CommandLineRunner {

    private final UserService userService;

    public VetClinicApplicationInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();

    }
}
