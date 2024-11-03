package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface GroupCommandService {

    Long handle(CreateGroupCommand command);
    Optional<Group> handle(UpdateGroupImageCommand command);
    Optional<Group> handle(UpdateGroupCommand command);
    void handle(DeleteGroupCommand command);
    String handle(GenerateInvitationCommand command);
}
