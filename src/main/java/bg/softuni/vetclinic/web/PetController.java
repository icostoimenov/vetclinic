package bg.softuni.vetclinic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pets")
public class PetController {

    @GetMapping("/my-pets")
    public String showPets() {
        return "my-pets";

    }

    @GetMapping("/add-pet")
    public String addNewPet(){
        return "add-pet";
    }



}
