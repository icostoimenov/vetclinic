package bg.softuni.vetclinic.web;

import bg.softuni.vetclinic.model.binding.StoryPostBindingModel;
import bg.softuni.vetclinic.model.service.StoryServiceModel;
import bg.softuni.vetclinic.service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;
    private final ModelMapper modelMapper;

    public StoryController(StoryService storyService, ModelMapper modelMapper) {
        this.storyService = storyService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("storyPostBindingModel")
    public StoryPostBindingModel createBindingModel() {
        return new StoryPostBindingModel();
    }

    @GetMapping("/show")
    public String showGallery(Model model) {
        model.addAttribute("stories", storyService.findAllStories());
        return "stories";
    }


    @GetMapping("/add")
    public String addStory() {
        return "post-story";
    }

    @PostMapping("/add")
    public String addStory(@Valid StoryPostBindingModel storyPostBindingModel, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("petAddBindingModel", storyPostBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.storyPostBindingModel", bindingResult);

            return "redirect:add";
        }
        StoryServiceModel storyServiceModel = modelMapper.map(storyPostBindingModel, StoryServiceModel.class);
        storyServiceModel.setAuthor(principal.getUsername());


        storyService.postStory(storyServiceModel);

        return "redirect:/stories/show";
    }

//    @GetMapping("/{id}")
//    public String readAStory(@PathVariable Long id, Model model) {
//
//
//    }

    @GetMapping("/{id}")
    public String increaseViews(@PathVariable long id, Model model
    ) {
        storyService.increaseViews(id);
        model.addAttribute("story", storyService.findStoryById(id));
        System.out.println();
        return "view-story";
    }

}
