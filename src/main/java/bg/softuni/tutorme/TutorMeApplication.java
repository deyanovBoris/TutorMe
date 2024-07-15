package bg.softuni.tutorme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TutorMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutorMeApplication.class, args);
    }

}
