package bg.softuni.postings.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "posting")
    private List<Comment> comments;

    public Posting() {
        comments = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public Posting setId(long id) {
        this.id = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public Posting setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getPostContent() {
        return postContent;
    }

    public Posting setPostContent(String postContent) {
        this.postContent = postContent;
        return this;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    public Posting setPostingDate(LocalDateTime postingDate) {
        this.postingDate = postingDate;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Posting setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }
}
