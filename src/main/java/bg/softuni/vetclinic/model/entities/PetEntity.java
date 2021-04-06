package bg.softuni.vetclinic.model.entities;

import bg.softuni.vetclinic.model.entities.enums.PetType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
public class PetEntity extends BaseEntity {

    private String name;
    private Integer age;
    private String gender;
    private PetType type;
    private String imageUrl;
    private UserEntity owner;
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
    public Integer getAge() {
        return age;
    }

    public PetEntity setAge(Integer age) {
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
    @Enumerated(EnumType.ORDINAL)
    public PetType getType() {
        return type;
    }

    public PetEntity setType(PetType type) {
        this.type = type;
        return this;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public PetEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @ManyToOne
    public UserEntity getOwner() {
        return owner;
    }

    public PetEntity setOwner(UserEntity owner) {
        this.owner = owner;
        return this;
    }
}
