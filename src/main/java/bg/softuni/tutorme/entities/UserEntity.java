package bg.softuni.tutorme.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id" )
    )
    private List<UserRoleEntity> roles;
    @OneToMany(mappedBy = "courseOwner")
    private List<Course> coursesTutoring;
    @ManyToMany
    @JoinTable(
            name = "users_attending_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> coursesAttending;

    @Column(nullable = false)
    private boolean isFeatured;

    @Basic
    private String profilePhotoUrl;

    @Column(columnDefinition = "TEXT")
    private String biography;

    public UserEntity() {
        roles = new ArrayList<>();
        coursesTutoring = new ArrayList<>();
        coursesAttending = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public UserEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public List<Course> getCoursesTutoring() {
        return coursesTutoring;
    }

    public UserEntity setCoursesTutoring(List<Course> coursesTutoring) {
        this.coursesTutoring = coursesTutoring;
        return this;
    }

    public List<Course> getCoursesAttending() {
        return coursesAttending;
    }

    public UserEntity setCoursesAttending(List<Course> coursesAttending) {
        this.coursesAttending = coursesAttending;
        return this;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public UserEntity setFeatured(boolean featured) {
        isFeatured = featured;
        return this;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public UserEntity setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
        return this;
    }

    public String getBiography() {
        return biography;
    }

    public UserEntity setBiography(String biography) {
        this.biography = biography;
        return this;
    }
}
