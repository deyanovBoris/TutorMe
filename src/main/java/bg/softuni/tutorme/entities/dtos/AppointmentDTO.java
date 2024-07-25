package bg.softuni.tutorme.entities.dtos;

import java.time.LocalDateTime;

public class AppointmentDTO {


    private long id;
    private String courseTitle;
    private LocalDateTime date;

    public AppointmentDTO() {

    }
    public long getId() {
        return id;
    }
    public AppointmentDTO setId(long id) {
        this.id = id;
        return this;
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
