package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.subjects.SubjectFeatureDTO;

import java.util.List;

public interface SubjectService {
    List<SubjectFeatureDTO> getFeaturedSubjects();

    List<SubjectFeatureDTO> getSubjects();

    String getSubjectNameById(long id);
}
