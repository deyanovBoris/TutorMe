package bg.softuni.tutorme.entities.dtos.appointment;

import bg.softuni.tutorme.entities.dtos.user.UserShortDTO;

import java.time.LocalDateTime;

public class AppointmentDetailDTO {

    private long id;

    private UserShortDTO madeBy;

    private UserShortDTO courseOwner;

    private String courseTitle;

    private LocalDateTime date;
    private String meetingLink;

    public AppointmentDetailDTO() {
    }

    public long getId() {
        return id;
    }

    public AppointmentDetailDTO setId(long id) {
        this.id = id;
        return this;
    }

    public UserShortDTO getMadeBy() {
        return madeBy;
    }

    public AppointmentDetailDTO setMadeBy(UserShortDTO madeBy) {
        this.madeBy = madeBy;
        return this;
    }

    public UserShortDTO getCourseOwner() {
        return courseOwner;
    }

    public AppointmentDetailDTO setCourseOwner(UserShortDTO courseOwner) {
        this.courseOwner = courseOwner;
        return this;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public AppointmentDetailDTO setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public AppointmentDetailDTO setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public AppointmentDetailDTO setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
        return this;
    }
}
