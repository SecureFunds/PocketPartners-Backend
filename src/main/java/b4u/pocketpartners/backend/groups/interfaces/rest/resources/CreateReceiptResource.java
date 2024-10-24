package b4u.pocketpartners.backend.groups.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record CreateReceiptResource(
        @NotNull String name,
        @NotNull String description,
        @NotNull String photo,
        @NotNull Long groupId,
        @NotNull Long userId
) {
}
