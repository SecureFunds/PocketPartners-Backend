package b4u.pocketpartners.backend.groups.interfaces.rest.transform;

import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.GroupMemberResource;

public class GroupMemberResourceFromEntityAssembler {
    public static GroupMemberResource fromEntityToResource(GroupMember resource) {
        return new GroupMemberResource(resource.getGroup().getId(), resource.getUserInformation().getId(), resource.getJoinedAt());
    }
}
