package bg.softuni.vetclinic.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class StoryPostBindingModel {
    private String title;
    private String storyText;
    private MultipartFile imageUrl;

    @Size(min = 10)
    public String getTitle() {
        return title;
    }

    public StoryPostBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @NotEmpty
    public String getStoryText() {
        return storyText;
    }

    public StoryPostBindingModel setStoryText(String storyText) {
        this.storyText = storyText;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public StoryPostBindingModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
