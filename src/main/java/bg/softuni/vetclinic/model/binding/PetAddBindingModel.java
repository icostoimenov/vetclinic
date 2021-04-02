package bg.softuni.vetclinic.model.binding;

import bg.softuni.vetclinic.model.entities.enums.PetType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PetAddBindingModel {

    private String name;
    private Integer age;
    private String gender;
    private PetType type;
    private String imageUrl;

    @Size(min = 3)
    public String getName() {
        return name;
    }

    public PetAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @Min(0)
    public Integer getAge() {
        return age;
    }

    public PetAddBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    @NotNull
    public String getGender() {
        return gender;
    }

    public PetAddBindingModel setGender(String gender) {
        this.gender = gender;
        return this;
    }

    @NotNull
    public PetType getType() {
        return type;
    }

    public PetAddBindingModel setType(PetType type) {
        this.type = type;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PetAddBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public String toString() {
        return "PetAddBindingModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", type=" + type +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
