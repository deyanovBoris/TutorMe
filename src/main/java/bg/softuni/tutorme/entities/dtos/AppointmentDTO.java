package bg.softuni.tutorme.entities.dtos;

public class AppointmentDTO {

    //todo finish defining dto
    private String courseName;

    public AppointmentDTO() {

    }

    public String getCourseName() {
        return courseName;
    }

    public AppointmentDTO setCourseName(String courseName) {
        this.courseName = courseName;
        return this;
    }
}
