package b4u.pocketpartners.backend.users.interfaces.rest.transform;

import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.entities.Role;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId(), user.getUsername(), roles);
    }
}
