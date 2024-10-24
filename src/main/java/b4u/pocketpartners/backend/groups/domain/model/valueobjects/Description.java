package b4u.pocketpartners.backend.groups.domain.model.valueobjects;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record Description(String description) {
    public Description {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
    }
}
