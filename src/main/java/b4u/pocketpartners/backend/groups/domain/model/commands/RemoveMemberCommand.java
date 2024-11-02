package b4u.pocketpartners.backend.groups.domain.model.commands;

public record RemoveMemberCommand(
        Long groupId,
        Long userId
) {}