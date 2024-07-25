package bg.softuni.tutorme.entities.dtos.tutor;

import bg.softuni.tutorme.entities.enums.ApplicationStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public class TutorApplicationDTO {
    private long id;

    private String applicantUsername;

    @NotBlank
    @Size(min = 5, max = 30)
    @Pattern(regexp = "[0-9\\s+()-]+", message = "Must be a valid phone number!")
    private String phone;

    @NotBlank
    @Size(min = 10, message = "Motivation must be at least 10 characters long!")
    private String motivation;

    @NotEmpty
    private List<Long> subjects;

    private List<String> subjectNames;

    @NotEmpty
    private String experience;

    private ApplicationStatusEnum status;

    public TutorApplicationDTO() {
    }

    public long getId() {
        return id;
    }

    public TutorApplicationDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getApplicantUsername() {
        return applicantUsername;
    }

    public TutorApplicationDTO setApplicantUsername(String applicantUsername) {
        this.applicantUsername = applicantUsername;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public TutorApplicationDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMotivation() {
        return motivation;
    }

    public TutorApplicationDTO setMotivation(String description) {
        this.motivation = description;
        return this;
    }

    public List<Long> getSubjects() {
        return subjects;
    }

    public TutorApplicationDTO setSubjects(List<Long> subjects) {
        this.subjects = subjects;
        return this;
    }

    public List<String> getSubjectNames() {
        return subjectNames;
    }

    public TutorApplicationDTO setSubjectNames(List<String> subjectNames) {
        this.subjectNames = subjectNames;
        return this;
    }

    public String getExperience() {
        return experience;
    }

    public TutorApplicationDTO setExperience(String experience) {
        this.experience = experience;
        return this;
    }

    public ApplicationStatusEnum getStatus() {
        return status;
    }

    public TutorApplicationDTO setStatus(ApplicationStatusEnum status) {
        this.status = status;
        return this;
    }
}
