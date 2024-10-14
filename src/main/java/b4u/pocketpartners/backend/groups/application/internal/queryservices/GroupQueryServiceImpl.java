package b4u.pocketpartners.backend.groups.application.internal.queryservices;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupQueryService;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GroupQueryServiceImpl implements GroupQueryService {
    private final GroupRepository groupRepository;

    public GroupQueryServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Optional<Group> handle(GetGroupByIdQuery query) {
        return groupRepository.findById(query.groupId());
    }

    @Override
    public List<Group> handle(GetAllGroupsQuery query) {
        return groupRepository.findAll();
    }
}
