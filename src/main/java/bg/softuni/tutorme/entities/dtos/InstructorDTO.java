package bg.softuni.tutorme.entities.dtos;

public class InstructorDTO {
    private String name;

    private String photoUrl;

    private String biography;

    public InstructorDTO() {
    }

    public String getName() {
        return name;
    }

    public InstructorDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public InstructorDTO setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public String getBiography() {
        return biography;
    }

    public InstructorDTO setBiography(String biography) {
        this.biography = biography;
        return this;
    }
}
