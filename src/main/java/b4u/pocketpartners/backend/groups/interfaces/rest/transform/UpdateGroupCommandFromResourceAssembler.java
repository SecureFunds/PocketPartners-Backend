package b4u.pocketpartners.backend.groups.interfaces.rest.transform;

import b4u.pocketpartners.backend.groups.domain.model.commands.UpdateGroupCommand;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.UpdateGroupResource;

public class UpdateGroupCommandFromResourceAssembler {
    public static UpdateGroupCommand toCommandFromResource(Long groupId, UpdateGroupResource resource) {
        return new UpdateGroupCommand(groupId, resource.name(), resource.description(), resource.groupPhotoUrl());
    }
}
