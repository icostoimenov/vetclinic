package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.service.TopStoryService;
import bg.softuni.vetclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final TopStoryService topStoryService;
    private final UserService userService;

    public HomeController(TopStoryService topStoryService, UserService userService) {
        this.topStoryService = topStoryService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("topStory", topStoryService.topStory());
        return "home";
    }

    @GetMapping("/contacts")
    public String contacts(){
        return "contacts";
    }

    @GetMapping("/doctors")
    public String ourTeam(Model model){
        model.addAttribute("doctors", userService.findAllDoctors());
        return "our-team";
    }
}
