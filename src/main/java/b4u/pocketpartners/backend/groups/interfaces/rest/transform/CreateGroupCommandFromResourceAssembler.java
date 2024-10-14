package b4u.pocketpartners.backend.groups.interfaces.rest.transform;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.CreateGroupResource;

public class CreateGroupCommandFromResourceAssembler {
    public static CreateGroupCommand toCommandFromResource(CreateGroupResource createGroupResource) {
        return new CreateGroupCommand(createGroupResource.name(), createGroupResource.groupPhoto(), createGroupResource.currency());
    }
}
