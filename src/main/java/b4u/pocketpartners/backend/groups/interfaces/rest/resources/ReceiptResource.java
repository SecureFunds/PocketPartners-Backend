package b4u.pocketpartners.backend.groups.interfaces.rest.resources;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record ReceiptResource(
        String name,
        String description,
        String photo,
        Long groupId,
        Long userId
) {
}
