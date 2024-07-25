package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.TutorApplication;
import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.tutor.TutorApplicationDTO;
import bg.softuni.tutorme.entities.enums.ApplicationStatusEnum;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.repositories.RoleRepository;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.repositories.TutorApplicationRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.TutorApplicationService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TutorApplicationServiceImpl implements TutorApplicationService {
    private final TutorApplicationRepository tutorApplicationRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final RoleRepository roleRepository;

    public TutorApplicationServiceImpl(TutorApplicationRepository tutorApplicationRepository, UserRepository userRepository, SubjectRepository subjectRepository, RoleRepository roleRepository) {
        this.tutorApplicationRepository = tutorApplicationRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean submitTutorApplication(TutorApplicationDTO data) {
        TutorApplication application = new TutorApplication();
        UserEntity applicant = this.userRepository.findByUsername(data.getApplicantUsername()).get();

        application
                .setApplicant(applicant)
                .setExperienced(data.getExperience().equals("yes"))
                .setMotivation(data.getMotivation())
                .setPhone(data.getPhone())
                .setSubjects(this.subjectRepository.findAllById(data.getSubjects()))
                .setCompleted(false)
                .setApplicationStatus(ApplicationStatusEnum.PENDING);

        this.tutorApplicationRepository.save(application);
        return true;
    }

    @Override
    public boolean hasPendingApplication(String username) {
        return this.tutorApplicationRepository.existsByApplicantUsernameAndApplicationStatus(username, ApplicationStatusEnum.PENDING);
    }

    @Override
    public boolean approveApplication(long applicationId) throws UserNotFoundException {
        Optional<TutorApplication> appById = this.tutorApplicationRepository.findById(applicationId);

        if (!appById.isPresent()){
            return false; //todo replace with exception
        }

        TutorApplication tutorApplication = appById.get();

        Optional<UserEntity> userById = this.userRepository.findById(tutorApplication.getApplicant().getId());
        if (!userById.isPresent()){
            throw new UserNotFoundException();
        }

        UserEntity user = userById.get();
        user.getRoles().add(this.roleRepository.findByRole(UserRoleEnum.TUTOR).get());

        tutorApplication.setCompleted(true);
        tutorApplication.setApplicationStatus(ApplicationStatusEnum.APPROVED);

        this.userRepository.save(user);
        this.tutorApplicationRepository.save(tutorApplication);

        return true;
    }

    @Override
    @Transactional
    public List<TutorApplicationDTO> getAllApplications() {
        return this.tutorApplicationRepository
                .findAll()
                .stream()
                .map(this::mapApplicationEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean rejectApplication(long applicationId) {
        Optional<TutorApplication> appById = this.tutorApplicationRepository.findById(applicationId);

        if (!appById.isPresent()){
            return false; //todo replace with exception
        }

        TutorApplication tutorApplication = appById.get();
        tutorApplication.setApplicationStatus(ApplicationStatusEnum.REJECTED);
        tutorApplication.setCompleted(true);

        this.tutorApplicationRepository.save(tutorApplication);

        return true;
    }

    @Override
    public boolean deleteApplication(long applicationId) {
        if (!tutorApplicationRepository.existsById(applicationId)){
            throw new ObjectNotFoundException(applicationId + "", applicationId);
        }

        String applicationStatus = this.tutorApplicationRepository
                .findById(applicationId)
                .get()
                .getApplicationStatus()
                .name();

        if (!applicationStatus.equals("PENDING")){
            this.tutorApplicationRepository.deleteById(applicationId);
        }

        return true;
    }

    private TutorApplicationDTO mapApplicationEntityToDTO(TutorApplication app){
        return new TutorApplicationDTO()
                .setId(app.getId())
                .setApplicantUsername(app.getApplicant().getUsername())
                .setPhone(app.getPhone())
                .setExperience(app.isExperienced() ? "Yes" : "No")
                .setMotivation(app.getMotivation())
                .setSubjectNames(app.getSubjects()
                        .stream()
                        .map(s -> s.getName())
                        .collect(Collectors.toList()))
                .setStatus(app.getApplicationStatus());
    }
}
