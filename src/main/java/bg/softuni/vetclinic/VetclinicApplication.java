package bg.softuni.vetclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VetclinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(VetclinicApplication.class, args);
    }

}
