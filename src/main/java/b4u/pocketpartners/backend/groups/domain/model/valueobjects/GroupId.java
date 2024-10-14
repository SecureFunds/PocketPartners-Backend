package b4u.pocketpartners.backend.groups.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record GroupId(
        @NotBlank
        String groupsId
) {
}
