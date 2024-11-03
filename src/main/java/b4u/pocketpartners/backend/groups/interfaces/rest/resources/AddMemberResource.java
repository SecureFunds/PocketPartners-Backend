package b4u.pocketpartners.backend.groups.interfaces.rest.resources;

import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupRole;

public record AddMemberResource(Long groupId, Long userId) {
}
