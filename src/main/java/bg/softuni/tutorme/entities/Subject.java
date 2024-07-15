package bg.softuni.tutorme.entities;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;

    @URL
    @Column(nullable = false, unique = true)
    private String pictureUrl;

    @Column(nullable = false)
    private boolean isFeatured;

    @ManyToMany(mappedBy = "subjects")
    private List<Course> courses;

    public Subject() {
    }

    public long getId() {
        return id;
    }

    public Subject setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Subject setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public Subject setFeatured(boolean featured) {
        isFeatured = featured;
        return this;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Subject setCourses(List<Course> courses) {
        this.courses = courses;
        return this;
    }
}
