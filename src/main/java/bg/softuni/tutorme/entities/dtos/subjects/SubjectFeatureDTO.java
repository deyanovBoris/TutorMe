package bg.softuni.tutorme.entities.dtos.subjects;

public class SubjectFeatureDTO {
    private long id;
    private String name;
    private String pictureUrl;

    public SubjectFeatureDTO() {
    }

    public long getId() {
        return id;
    }

    public SubjectFeatureDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SubjectFeatureDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public SubjectFeatureDTO setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }
}
