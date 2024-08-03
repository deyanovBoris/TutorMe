package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.Subject;
import bg.softuni.tutorme.entities.dtos.subjects.SubjectFeatureDTO;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.service.SubjectService;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    private final Random random;

    public SubjectServiceImpl(SubjectRepository subjectRepository, ModelMapper modelMapper, Random random) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
        this.random = random;
    }

    @Override
    public List<SubjectFeatureDTO> getFeaturedSubjects() {
        return this.subjectRepository
                .findByIsFeaturedTrue()
                .stream()
                .sorted(Comparator.comparingInt(e -> random.nextInt()))
                .limit(3)
                .map(s -> modelMapper.map(s, SubjectFeatureDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectFeatureDTO> getSubjects() {
        return this.subjectRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Subject::getName))
                .map(s -> modelMapper.map(s, SubjectFeatureDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getSubjectNameById(long id) {
        return this.subjectRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Subject"))
                .getName();
    }
}
