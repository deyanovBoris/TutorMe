package bg.softuni.tutorme.entities.dtos.courses;

import bg.softuni.tutorme.entities.enums.CourseType;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class CourseAddDTO {

    @NotEmpty
    @Size(min = 5, max = 40)
    private String title;
    @NotEmpty
    private List<Long> subjects;

    private String ownerUsername;

    @NotEmpty
    @Size(min = 10, max = 250)
    private String description;

    @NotNull
    private CourseType courseType;

    @DateTimeFormat
    @FutureOrPresent
    @NotNull
    private LocalDate startDate;

    @DateTimeFormat
    @Future
    @NotNull
    private LocalDate endDate;

    @NotEmpty
    @URL
    private String courseImageUrl;

    public CourseAddDTO() {
    }

    public String getTitle() {
        return title;
    }

    public CourseAddDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Long> getSubjects() {
        return subjects;
    }

    public CourseAddDTO setSubjects(List<Long> subjects) {
        this.subjects = subjects;
        return this;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public CourseAddDTO setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public CourseAddDTO setCourseType(CourseType courseType) {
        this.courseType = courseType;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public CourseAddDTO setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CourseAddDTO setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public CourseAddDTO setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
        return this;
    }
}
