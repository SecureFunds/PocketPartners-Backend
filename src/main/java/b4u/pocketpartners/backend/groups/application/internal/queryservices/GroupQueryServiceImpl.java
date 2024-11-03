package b4u.pocketpartners.backend.groups.application.internal.queryservices;

import b4u.pocketpartners.backend.groups.domain.exceptions.GroupNotFoundException;
import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsByUserIdQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupByIdQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupInvitationLinkQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupQueryService;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupMemberRepository;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class GroupQueryServiceImpl implements GroupQueryService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public GroupQueryServiceImpl(GroupRepository groupRepository, GroupMemberRepository groupMemberRepository) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
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
