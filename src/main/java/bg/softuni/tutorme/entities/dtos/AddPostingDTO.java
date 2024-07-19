package bg.softuni.tutorme.entities.dtos;

public class AddPostingDTO {

    private long userId;

    private String postContent;

    public AddPostingDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public AddPostingDTO setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getPostContent() {
        return postContent;
    }

    public AddPostingDTO setPostContent(String postContent) {
        this.postContent = postContent;
        return this;
    }
}
