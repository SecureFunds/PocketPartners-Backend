package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupMemberCommand;
import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;

import java.util.Optional;

public interface GroupMemberCommandService {
    Optional<GroupMember> handle(CreateGroupMemberCommand command);
}
