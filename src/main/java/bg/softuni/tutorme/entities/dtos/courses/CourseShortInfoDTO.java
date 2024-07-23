package bg.softuni.tutorme.entities.dtos.courses;

import java.time.LocalDate;

public class CourseShortInfoDTO {
    private long id;

    private String title;

    private String description;

    private String courseImageUrl;

    private String instructorName;

    private long durationInWeeks;

    private LocalDate startDate;
    private LocalDate endDate;

    public CourseShortInfoDTO() {
    }

    public long getId() {
        return id;
    }

    public CourseShortInfoDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CourseShortInfoDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseShortInfoDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public CourseShortInfoDTO setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
        return this;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public CourseShortInfoDTO setInstructorName(String instructorName) {
        this.instructorName = instructorName;
        return this;
    }

    public long getDurationInWeeks() {
        return durationInWeeks;
    }

    public CourseShortInfoDTO setDurationInWeeks(long durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public CourseShortInfoDTO setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CourseShortInfoDTO setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
