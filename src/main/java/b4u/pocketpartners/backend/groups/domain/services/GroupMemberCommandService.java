package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.commands.*;
import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface GroupMemberCommandService {
    Optional<GroupMember> handle(AddMemberCommand command);
    void handle (RemoveMemberCommand command);

    Optional<GroupMember> handle(JoinGroupWithTokenCommand command);
}
