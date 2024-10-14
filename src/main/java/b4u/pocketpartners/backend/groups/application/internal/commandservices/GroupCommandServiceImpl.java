package b4u.pocketpartners.backend.groups.application.internal.commandservices;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.domain.services.GroupCommandService;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupCommandServiceImpl implements GroupCommandService {

    private final GroupRepository groupRepository;

    public GroupCommandServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Optional<Group> handle(CreateGroupCommand command) {
        var group = new Group(command);
        groupRepository.save(group);
        return Optional.of(group);
    }

}
