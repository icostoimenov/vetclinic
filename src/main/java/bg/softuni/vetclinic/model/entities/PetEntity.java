package bg.softuni.vetclinic.model.entities;

import bg.softuni.vetclinic.model.enums.PetType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
public class PetEntity extends BaseEntity {

    private String name;
    private int age;
    private String gender;
    private PetType type;
    private String imageUrl;
    private String ownerName;
    private List<String> diagnoses = new ArrayList<>();

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public PetEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(nullable = false)
    public int getAge() {
        return age;
    }

    public PetEntity setAge(int age) {
        this.age = age;
        return this;
    }

    @Column(nullable = false)
    public String getGender() {
        return gender;
    }

    public PetEntity setGender(String gender) {
        this.gender = gender;
        return this;
    }

    @Column(nullable = false)
    public PetType getType() {
        return type;
    }

    public PetEntity setType(PetType type) {
        this.type = type;
        return this;
    }

    @Column(nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public PetEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Column(nullable = false)
    public String getOwnerName() {
        return ownerName;
    }

    public PetEntity setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    @ElementCollection
    public List<String> getDiagnoses() {
        return diagnoses;
    }

    public PetEntity setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }
}
