package bg.softuni.vetclinic.model.service;

import org.springframework.web.multipart.MultipartFile;

public class StoryServiceModel {

    private String title;
    private String storyText;
    private String author;
    private MultipartFile imageUrl;

    public String getTitle() {
        return title;
    }

    public StoryServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStoryText() {
        return storyText;
    }

    public StoryServiceModel setStoryText(String storyText) {
        this.storyText = storyText;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public StoryServiceModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public StoryServiceModel setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
