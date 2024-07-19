package bg.softuni.postings.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "user_id")
    private long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(optional = false)
    private Posting posting;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public Comment setId(long id) {
        this.id = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public Comment setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Posting getPosting() {
        return posting;
    }

    public Comment setPosting(Posting posting) {
        this.posting = posting;
        return this;
    }
}
