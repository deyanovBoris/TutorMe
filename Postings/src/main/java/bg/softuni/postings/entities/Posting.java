package bg.softuni.postings.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "postings")
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
    @Column(nullable = false, name = "user_id")
    private long userId;

    @Column(nullable = false, name = "post_content", columnDefinition = "TEXT")
    private String postContent;

    @Column(nullable = false, name = "posting_date")
    private LocalDateTime postingDate;





}
