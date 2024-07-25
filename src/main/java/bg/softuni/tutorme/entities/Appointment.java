package bg.softuni.tutorme.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, name = "date_utc")
    private LocalDateTime date;
    @Basic
    private String description;
    @ManyToOne(optional = false)
    private UserEntity user;
    @ManyToOne(optional = false)
    private Course course;

    public Appointment() {
    }

    public long getId() {
        return id;
    }

    public Appointment setId(long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Appointment setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Appointment setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public Appointment setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public Course getCourse() {
        return course;
    }

    public Appointment setCourse(Course course) {
        this.course = course;
        return this;
    }
}
