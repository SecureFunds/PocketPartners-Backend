package b4u.pocketpartners.backend.users.domain.services;

import b4u.pocketpartners.backend.users.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
