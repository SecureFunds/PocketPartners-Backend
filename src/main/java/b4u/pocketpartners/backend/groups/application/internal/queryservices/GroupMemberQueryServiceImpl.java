package b4u.pocketpartners.backend.groups.application.internal.queryservices;

import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsOfUserByUserInformationId;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllMembersInGroupQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupMemberQueryService;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberQueryServiceImpl implements GroupMemberQueryService {

    private final GroupMemberRepository groupMemberRepository;

    public GroupMemberQueryServiceImpl(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    @Override
    public List<GroupMember> handle(GetAllMembersInGroupQuery query) {
        return groupMemberRepository.findAllByGroupId(query.groupId());
    }

    @Override
    public List<GroupMember> handle(GetAllGroupsOfUserByUserInformationId query) {
        return groupMemberRepository.findAllByUserInformationUserId(query.userId());
    }
}
