package bg.softuni.vetclinic.model.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String email;
    private String fullName;
    private String password;
    private int phoneNumber;
    private Set<PetEntity> pets;
    private List<UserRoleEntity> roles = new ArrayList<>();

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(nullable = false)
    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<PetEntity> getPets() {
        return pets;
    }

    public UserEntity setPets(Set<PetEntity> pets) {
        this.pets = pets;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    @Column(nullable = false)
    public int getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    public UserEntity addRole(UserRoleEntity roleEntity) {
        this.roles.add(roleEntity);
        return this;
    }
}
