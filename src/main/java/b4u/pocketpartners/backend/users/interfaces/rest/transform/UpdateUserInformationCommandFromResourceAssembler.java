package b4u.pocketpartners.backend.users.interfaces.rest.transform;

import b4u.pocketpartners.backend.users.domain.model.commands.UpdateUserInformationCommand;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UpdateUserInformationResource;

public class UpdateUserInformationCommandFromResourceAssembler {
    public static UpdateUserInformationCommand toCommandfromResource(Long userId, UpdateUserInformationResource resource) {
        return new UpdateUserInformationCommand(userId, resource.firstName(), resource.lastName(), resource.phoneNumber(), resource.photo(), resource.email());
    }
}