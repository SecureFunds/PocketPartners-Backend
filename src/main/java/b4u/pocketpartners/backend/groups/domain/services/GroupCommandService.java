package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;

import java.util.Optional;

public interface GroupCommandService {

    Optional<Group> handle(CreateGroupCommand command);
}
