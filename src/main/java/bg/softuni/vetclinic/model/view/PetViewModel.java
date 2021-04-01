package bg.softuni.vetclinic.model.view;

import bg.softuni.vetclinic.model.entities.enums.PetType;

public class PetViewModel {

    private String name;
    private int age;
    private String gender;
    private PetType type;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public PetViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public PetViewModel setAge(int age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PetViewModel setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public PetType getType() {
        return type;
    }

    public PetViewModel setType(PetType type) {
        this.type = type;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PetViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public String toString() {
        return "PetViewModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", type=" + type +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
