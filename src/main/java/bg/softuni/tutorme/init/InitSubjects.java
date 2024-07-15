package bg.softuni.tutorme.init;

import bg.softuni.tutorme.entities.Subject;
import bg.softuni.tutorme.repositories.SubjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class InitSubjects {
    private final SubjectRepository subjectRepository;
    private static final Map<String, String> INITIAL_SUBJECT_URL_MAP = Map.of(
            "Mathematics", "https://images.theconversation.com/files/139426/original/image-20160927-14593-1rf92dt.jpg?ixlib=rb-4.1.0&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip",
            "Physics", "https://t4.ftcdn.net/jpg/02/14/56/75/240_F_214567514_hGbTMUq06pJIGKiXA248l43E3hU9Q08x.jpg",
            "Chemistry", "https://img.freepik.com/premium-photo/investigations-defunct-chemistry-facility-laboratory-chemistry-instruction_410516-4221.jpg",
            "Biology", "https://static.vecteezy.com/system/resources/thumbnails/023/042/077/small_2x/human-cell-biology-dna-strands-molecular-structure-illustration-generate-ai-free-photo.jpg",
            "Computer Science", "https://upload.wikimedia.org/wikipedia/commons/e/e1/Computer_science_and_engineering.jpg",
            "History", "https://www1.wellesley.edu/albright/sites/www.wellesley.edu.albright/files/styles/large/public/field/image/Image-for-VD-post_smaller.jpg?itok=CxzOi_GV",
            "Geography", "https://educationblog.oup.com/wp-content/uploads/2015/09/oup_58381-705x435.jpg",
            "Literature", "https://t3.ftcdn.net/jpg/03/33/45/40/360_F_333454009_nlCLykXBPzOLMFCTbo3PHUQJnJrDtPQX.jpg",
            "Art", "https://www.parblo.com/cdn/shop/articles/5_Famous_Impressionist_Artists_and_Their_Masterpieces_1024x.png?v=1561469589",
            "Physical Education", "https://t3.ftcdn.net/jpg/05/13/76/12/360_F_513761212_i3z6vHUNV0bQ8pjkBGO3yrpLg8zs4mFK.jpg"
    );

    public InitSubjects(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    @Bean
    public void initializeSubjects(){
        if (this.subjectRepository.count() == 0){
            this.subjectRepository.saveAll(makeSubjectEntitiesList());
        }
    }

    private static List<Subject> makeSubjectEntitiesList() {
        return INITIAL_SUBJECT_URL_MAP
                .entrySet()
                .stream()
                .map(entry -> new Subject()
                        .setName(entry.getKey())
                        .setPictureUrl(entry.getValue())
                        .setFeatured(ThreadLocalRandom.current().nextBoolean()))
                .collect(Collectors.toList());
    }
}
