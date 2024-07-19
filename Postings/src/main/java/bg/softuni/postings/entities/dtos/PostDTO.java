package bg.softuni.postings.entities.dtos;

import java.time.LocalDateTime;

public class PostDTO {

    private long id;
    private long userId;

    private String postContent;

    private LocalDateTime postingDate;

    public PostDTO() {
    }

    public long getId() {
        return id;
    }

    public PostDTO setId(long id) {
        this.id = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public PostDTO setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getPostContent() {
        return postContent;
    }

    public PostDTO setPostContent(String postContent) {
        this.postContent = postContent;
        return this;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    public PostDTO setPostingDate(LocalDateTime postingDate) {
        this.postingDate = postingDate;
        return this;
    }
}
