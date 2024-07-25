package bg.softuni.tutorme.entities.dtos;

import java.time.LocalDateTime;

public class AppointmentCourseDTO {

    private long id;
    private String studentName;

    private String username;

    private LocalDateTime date;

    public AppointmentCourseDTO() {
    }

    public long getId() {
        return id;
    }

    public AppointmentCourseDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getStudentName() {
        return studentName;
    }

    public AppointmentCourseDTO setStudentName(String studentName) {
        this.studentName = studentName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AppointmentCourseDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public AppointmentCourseDTO setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }
}
