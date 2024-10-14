package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.commands.AddGroupOperationCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.DeleteGroupOperationCommand;

public interface GroupOperationCommandService {
    Long handle(AddGroupOperationCommand command);
    void handle(DeleteGroupOperationCommand command);
}
