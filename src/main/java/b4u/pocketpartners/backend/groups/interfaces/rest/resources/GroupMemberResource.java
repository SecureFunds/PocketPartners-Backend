package b4u.pocketpartners.backend.groups.interfaces.rest.resources;

import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupRole;

public record GroupMemberResource (Long groupId, Long userId, String fullName, GroupRole role,  java.util.Date joinedAt){
}
