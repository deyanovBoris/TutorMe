package bg.softuni.tutorme.entities.dtos.courses;

import bg.softuni.tutorme.entities.dtos.appointment.AppointmentCourseDTO;
import bg.softuni.tutorme.entities.dtos.InstructorDTO;
import bg.softuni.tutorme.entities.dtos.StudentsShortInfoDto;

import java.util.List;

public class CourseInfoDTO {
    private long id;

    private String title;

    private String imageUrl;

    private String description;

    private List<StudentsShortInfoDto> students;

    private InstructorDTO instructor;

    private List<AppointmentCourseDTO> appointments;

    public CourseInfoDTO() {
    }

    public String getTitle() {
        return title;
    }

    public CourseInfoDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public long getId() {
        return id;
    }

    public CourseInfoDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CourseInfoDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseInfoDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<StudentsShortInfoDto> getStudents() {
        return students;
    }

    public CourseInfoDTO setStudents(List<StudentsShortInfoDto> students) {
        this.students = students;
        return this;
    }

    public InstructorDTO getInstructor() {
        return instructor;
    }

    public CourseInfoDTO setInstructor(InstructorDTO instructor) {
        this.instructor = instructor;
        return this;
    }

    public List<AppointmentCourseDTO> getAppointments() {
        return appointments;
    }

    public CourseInfoDTO setAppointments(List<AppointmentCourseDTO> appointments) {
        this.appointments = appointments;
        return this;
    }
}
