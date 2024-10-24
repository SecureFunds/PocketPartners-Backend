package b4u.pocketpartners.backend.groups.domain.model.valueobjects;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record Photo(String photo) {
    public Photo {
        if (photo == null || photo.isBlank()) {
            throw new IllegalArgumentException("Photo cannot be null or blank");
        }
    }
}
