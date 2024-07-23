package bg.softuni.tutorme.entities.dtos;

import bg.softuni.tutorme.entities.dtos.courses.CourseShortInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class UserProfileDTO {

    private long id;
    private String username;

    private String profilePhotoUrl;

    private String biography;

    private List<CourseShortInfoDTO> coursesAttending;
    private List<CourseShortInfoDTO> coursesTutoring;

    private List<AppointmentDTO> appointments;

    public UserProfileDTO() {
        coursesAttending = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public UserProfileDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserProfileDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public UserProfileDTO setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
        return this;
    }

    public List<CourseShortInfoDTO> getCoursesAttending() {
        return coursesAttending;
    }

    public UserProfileDTO setCoursesAttending(List<CourseShortInfoDTO> coursesAttending) {
        this.coursesAttending = coursesAttending;
        return this;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public UserProfileDTO setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
        return this;
    }

    public String getBiography() {
        return biography;
    }

    public UserProfileDTO setBiography(String biography) {
        this.biography = biography;
        return this;
    }

    public List<CourseShortInfoDTO> getCoursesTutoring() {
        return coursesTutoring;
    }

    public UserProfileDTO setCoursesTutoring(List<CourseShortInfoDTO> coursesTutoring) {
        this.coursesTutoring = coursesTutoring;
        return this;
    }
}
