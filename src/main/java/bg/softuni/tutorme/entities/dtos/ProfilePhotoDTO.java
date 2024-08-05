package bg.softuni.tutorme.entities.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public class ProfilePhotoDTO {

    @URL
    @NotEmpty
    private String profilePhotoUrl;

    public ProfilePhotoDTO() {
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public ProfilePhotoDTO setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
        return this;
    }
}
