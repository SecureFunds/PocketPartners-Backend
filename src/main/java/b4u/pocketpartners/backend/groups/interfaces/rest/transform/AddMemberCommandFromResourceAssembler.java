package b4u.pocketpartners.backend.groups.interfaces.rest.transform;

import b4u.pocketpartners.backend.groups.domain.model.commands.AddMemberCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.AddMemberResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.CreateGroupResource;

public class AddMemberCommandFromResourceAssembler {
    public static AddMemberCommand toCommandFromResource(AddMemberResource addMemberResource) {
        return new AddMemberCommand(addMemberResource.groupId(), addMemberResource.userId());
    }
}



