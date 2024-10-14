package b4u.pocketpartners.backend.groups.domain.model.valueobjects;

public record GroupPhoto(String photoLink) {
    public GroupPhoto {
        if (photoLink == null || photoLink.isBlank()) {
            throw new IllegalArgumentException("Photo link must not be null or blank");
        }
    }

    public GroupPhoto() {
        this("No link");
    }

    public String getPhotoLink() {
        return photoLink;
    }
}
