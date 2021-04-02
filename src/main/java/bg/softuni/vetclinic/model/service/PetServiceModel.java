package bg.softuni.vetclinic.model.service;

import bg.softuni.vetclinic.model.entities.enums.PetType;


public class PetServiceModel {

    private String name;
    private Integer age;
    private String gender;
    private PetType type;
    private String imageUrl;
    private String owner;

    public PetServiceModel() {
    }

    public String getName() {
        return name;
    }

    public PetServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public PetServiceModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PetServiceModel setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public PetType getType() {
        return type;
    }

    public PetServiceModel setType(PetType type) {
        this.type = type;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PetServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public PetServiceModel setOwner(String owner) {
        this.owner = owner;
        return this;
    }
}
