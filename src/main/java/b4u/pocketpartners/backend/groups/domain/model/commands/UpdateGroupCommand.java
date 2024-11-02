package b4u.pocketpartners.backend.groups.domain.model.commands;

public record UpdateGroupCommand(
        Long groupId,
        String name,
        String groupPhotoUrl,
        String description

        ) {}