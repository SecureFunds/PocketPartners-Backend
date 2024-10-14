package b4u.pocketpartners.backend.users.domain.services;

import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import b4u.pocketpartners.backend.users.domain.model.commands.CreateUserInformationCommand;
import b4u.pocketpartners.backend.users.domain.model.commands.DeleteUserInformationCommand;
import b4u.pocketpartners.backend.users.domain.model.commands.UpdateUserInformationCommand;

import java.util.Optional;

public interface UserInformationCommandService {
    Optional<UserInformation> handle(CreateUserInformationCommand command);
    Optional<UserInformation> handle(DeleteUserInformationCommand command);
    Optional<UserInformation> handle(UpdateUserInformationCommand command);
}
