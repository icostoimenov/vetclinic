package bg.softuni.vetclinic.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "stories")
public class StoryEntity extends BaseEntity {

    private String title;
    private String storyText;
    private LocalDate createdOn;
    private UserEntity author;
    private String imageUrl;
    private int views;

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public StoryEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(nullable = false, length = 1000)
    public String getStoryText() {
        return storyText;
    }

    public StoryEntity setStoryText(String storyText) {
        this.storyText = storyText;
        return this;
    }

    @Column(nullable = false)
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public StoryEntity setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }
    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public StoryEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    @Column(nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public StoryEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public int getViews() {
        return views;
    }

    public StoryEntity setViews(int views) {
        this.views = views;
        return this;
    }
}
