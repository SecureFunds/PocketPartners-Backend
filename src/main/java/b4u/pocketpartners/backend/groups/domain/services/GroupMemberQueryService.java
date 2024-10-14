package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsOfUserByUserInformationId;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllMembersInGroupQuery;

import java.util.List;

public interface GroupMemberQueryService {
    List<GroupMember> handle(GetAllMembersInGroupQuery query);
    List<GroupMember> handle(GetAllGroupsOfUserByUserInformationId query);
}
