package bg.softuni.tutorme.entities.dtos;

public class UserShortDTO {

    private long id;
    private String profilePhotoUrl;
    private String username;

    public UserShortDTO() {
    }

    public long getId() {
        return id;
    }

    public UserShortDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public UserShortDTO setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserShortDTO setUsername(String username) {
        this.username = username;
        return this;
    }
}
