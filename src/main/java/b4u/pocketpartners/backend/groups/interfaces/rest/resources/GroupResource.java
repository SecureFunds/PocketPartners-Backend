package b4u.pocketpartners.backend.groups.interfaces.rest.resources;

import b4u.pocketpartners.backend.groups.domain.model.entities.Currency;

import java.util.Date;
import java.util.Set;

public record GroupResource(
        Long id,
        String name,
       String description,
        String groupPhoto,
        Date createdAt,
        Date updatedAt
) {
}
