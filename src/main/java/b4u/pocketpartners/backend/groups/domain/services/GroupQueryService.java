package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupByIdQuery;

import java.util.List;
import java.util.Optional;

public interface GroupQueryService {
    Optional<Group> handle(GetGroupByIdQuery query);
    List<Group> handle(GetAllGroupsQuery query);
}
