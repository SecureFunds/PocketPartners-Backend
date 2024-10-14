package b4u.pocketpartners.backend.users.interfaces.rest.transform;

import b4u.pocketpartners.backend.users.domain.model.commands.SignInCommand;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}