package b4u.pocketpartners.backend.users.domain.services;

import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.queries.GetAllUsersQuery;
import b4u.pocketpartners.backend.users.domain.model.queries.GetUserByIdQuery;
import b4u.pocketpartners.backend.users.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
}

