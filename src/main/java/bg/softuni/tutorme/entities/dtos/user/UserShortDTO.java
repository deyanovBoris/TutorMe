package bg.softuni.tutorme.entities.dtos.user;

public class UserShortDTO {

    private long id;
    private String profilePhotoUrl;
    private String username;
    private String email;
    private String fullName;

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

    public String getEmail() {
        return email;
    }

    public UserShortDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserShortDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
