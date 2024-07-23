package bg.softuni.tutorme.entities;

import bg.softuni.tutorme.entities.enums.ApplicationStatusEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tutor_applications")
public class TutorApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    private UserEntity applicant;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String motivation;

    @ManyToMany
    @JoinTable(
            name = "applicant_subjects",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id" )
    )
    private List<Subject> subjects;

    @Column(nullable = false)
    private boolean isExperienced;

    @Column(nullable = false)
    private boolean isCompleted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatusEnum applicationStatus;

    public TutorApplication() {
    }

    public long getId() {
        return id;
    }

    public TutorApplication setId(long id) {
        this.id = id;
        return this;
    }

    public UserEntity getApplicant() {
        return applicant;
    }

    public TutorApplication setApplicant(UserEntity applicant) {
        this.applicant = applicant;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public TutorApplication setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMotivation() {
        return motivation;
    }

    public TutorApplication setMotivation(String motivation) {
        this.motivation = motivation;
        return this;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public TutorApplication setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public boolean isExperienced() {
        return isExperienced;
    }

    public TutorApplication setExperienced(boolean experienced) {
        isExperienced = experienced;
        return this;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public TutorApplication setCompleted(boolean completed) {
        isCompleted = completed;
        return this;
    }

    public ApplicationStatusEnum getApplicationStatus() {
        return applicationStatus;
    }

    public TutorApplication setApplicationStatus(ApplicationStatusEnum applicationStatus) {
        this.applicationStatus = applicationStatus;
        return this;
    }
}
