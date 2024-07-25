package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.TutorApplication;
import bg.softuni.tutorme.entities.enums.ApplicationStatusEnum;
import bg.softuni.tutorme.repositories.TutorApplicationRepository;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;

@Component
public class ScheduledTasks {

    private final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private final TutorApplicationRepository tutorApplicationRepository;

    public ScheduledTasks(TutorApplicationRepository tutorApplicationRepository) {
        this.tutorApplicationRepository = tutorApplicationRepository;
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void clearUnapprovedTutorRequests(){
        logger.info("Performing cleaning of unapproved tutor requests");

        List<TutorApplication> rejectedApplications = this.tutorApplicationRepository
                .findAllByIsCompletedTrueAndApplicationStatus(ApplicationStatusEnum.REJECTED);

        String ids = rejectedApplications
                .stream()
                .map(app -> app.getId())
                .collect(Collectors.toList())
                .toString();
        logger.info("Deleting request ids: " + ids);

        this.tutorApplicationRepository.deleteAll(rejectedApplications);
    }
}
