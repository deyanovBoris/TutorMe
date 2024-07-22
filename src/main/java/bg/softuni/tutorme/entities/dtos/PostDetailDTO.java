package bg.softuni.tutorme.entities.dtos;

import java.time.LocalDateTime;

public class PostDetailDTO {

    private long id;
    private String postContent;
    private long userId;
    private UserShortDTO user;
    private LocalDateTime postingDate;

    public PostDetailDTO() {
        user = new UserShortDTO();
    }

    public long getId() {
        return id;
    }

    public PostDetailDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getPostContent() {
        return postContent;
    }

    public PostDetailDTO setPostContent(String postContent) {
        this.postContent = postContent;
        return this;
    }

    public UserShortDTO getUser() {
        return user;
    }

    public PostDetailDTO setUser(UserShortDTO user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    public PostDetailDTO setPostingDate(LocalDateTime postingDate) {
        this.postingDate = postingDate;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public PostDetailDTO setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
