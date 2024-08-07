package bg.softuni.tutorme.entities.dtos;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class MeetingLinkDTO {
    @NotEmpty
    @URL
    private String meetingUrl;

    public MeetingLinkDTO() {
    }

    public String getMeetingUrl() {

        return meetingUrl;
    }

    public MeetingLinkDTO setMeetingUrl(String meetingUrl) {
        this.meetingUrl = meetingUrl;
        return this;
    }
}
