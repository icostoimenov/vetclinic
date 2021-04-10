package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.service.TopStoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final TopStoryService topStoryService;

    public HomeController(TopStoryService topStoryService) {
        this.topStoryService = topStoryService;
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
}
