package bg.softuni.tutorme.entities.dtos;

import java.util.List;

public class TutorFeatureDTO {
    private String name;
    private String username;
    private String biography;
    private String profilePhotoUrl;

    public TutorFeatureDTO() {
    }

    public String getName() {
        return name;
    }

    public TutorFeatureDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public TutorFeatureDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getBiography() {
        return biography;
    }

    public TutorFeatureDTO setBiography(String biography) {
        this.biography = biography;
        return this;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public TutorFeatureDTO setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
        return this;
    }
}
