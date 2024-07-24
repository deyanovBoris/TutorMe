package bg.softuni.tutorme.entities;

import bg.softuni.tutorme.entities.enums.CourseType;
import bg.softuni.tutorme.entities.enums.TermEnum;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String title;
    @ManyToMany
    @JoinTable(
            name = "courses_subjects",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseType courseType;
    @ManyToOne
    private UserEntity courseOwner;
    @ManyToMany(mappedBy = "coursesAttending")
    private List<UserEntity> students;
    @OneToMany(mappedBy = "course")
    private List<Appointment> appointments;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @URL
    private String courseImageUrl;

    public Course() {
        students = new ArrayList<>();
        appointments = new ArrayList<>();
        subjects = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public Course setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Course setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getId() {
        return id;
    }

    public Course setId(long id) {
        this.id = id;
        return this;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public Course setCourseType(CourseType courseType) {
        this.courseType = courseType;
        return this;
    }

    public UserEntity getCourseOwner() {
        return courseOwner;
    }

    public Course setCourseOwner(UserEntity courseOwner) {
        this.courseOwner = courseOwner;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Course setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Course setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public Course setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
        return this;
    }

    public List<UserEntity> getStudents() {
        return students;
    }

    public Course setStudents(List<UserEntity> students) {
        this.students = students;
        return this;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public Course setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public Course setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }
}
