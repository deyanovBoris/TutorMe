package bg.softuni.tutorme.entities.dtos;

import java.time.LocalDateTime;

public class AppointmentDTO {

    //todo finish defining dto
    private String courseTitle;
    private LocalDateTime date;

    public AppointmentDTO() {

    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public AppointmentDTO setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public AppointmentDTO setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }
}
