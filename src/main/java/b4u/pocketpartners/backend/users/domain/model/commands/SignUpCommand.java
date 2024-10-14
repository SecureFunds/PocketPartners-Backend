package b4u.pocketpartners.backend.users.domain.model.commands;

import b4u.pocketpartners.backend.users.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<Role> roles) {
}
