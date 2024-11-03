package b4u.pocketpartners.backend.groups.domain.model.commands;

import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupRole;

public record AddMemberCommand(Long groupId, Long userId ) {
    public AddMemberCommand {
        if (groupId == null || userId == null ) {
          throw new IllegalArgumentException( "Group, user, and requester IDs cannot be null." );
        }
    }
}