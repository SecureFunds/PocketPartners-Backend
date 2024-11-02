package b4u.pocketpartners.backend.groups.domain.model.commands;

import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Currencies;

import java.util.Set;

public record CreateGroupCommand(String name, String groupPhoto, String description, Long adminId) {
    public CreateGroupCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name cannot be null or empty");
        }

        if (adminId == null || adminId < 0) {
            throw new IllegalArgumentException("Admin id cannot be negative");
        }
    }

}
