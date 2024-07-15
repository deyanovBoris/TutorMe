package bg.softuni.tutorme.entities.dtos;

public class StudentsShortInfoDto {
    private String name;

    private String photoUrl;

    public StudentsShortInfoDto() {
    }

    public String getName() {
        return name;
    }

    public StudentsShortInfoDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public StudentsShortInfoDto setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }
}
