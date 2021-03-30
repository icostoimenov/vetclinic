package bg.softuni.vetclinic.model.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {


    private long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false)
    public long getId() {
        return id;
    }

    public BaseEntity setId(long id) {
        this.id = id;
        return this;
    }
}

