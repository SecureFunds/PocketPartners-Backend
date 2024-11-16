package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.entities.Role;
import b4u.pocketpartners.backend.users.domain.model.valueobjects.Roles;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {
    
    @Test
    void givenValidUserDetails_whenCreateUser_thenUserCreated() {
        // Arrange & Act
        User user = new User("john_doe", "securePassword");

        // Assert
        assertEquals("john_doe", user.getUsername());
        assertEquals("securePassword", user.getPassword());
        assertTrue(user.getRoles().isEmpty());
    }



    @Test
    void givenRoles_whenAddRole_thenRoleAdded() {
        // Arrange
        User user = new User("john_doe", "securePassword");
        Role role = new Role(Roles.ROLE_USER);

        // Act
        user.addRole(role);

        // Assert
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    void givenRoleList_whenAddRoles_thenRolesAdded() {
        // Arrange
        User user = new User("john_doe", "securePassword");
        List<Role> roles = List.of(new Role(Roles.ROLE_USER), new Role(Roles.ROLE_ADMIN));

        // Act
        user.addRoles(roles);

        // Assert
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().containsAll(roles));
    }

    @Test
    void givenDuplicateRoles_whenAddRoles_thenNoDuplicates() {
        // Arrange
        User user = new User("john_doe", "securePassword");
        Role role = new Role(Roles.ROLE_USER);

        // Act
        user.addRole(role);
        user.addRole(role);

        // Assert
        assertEquals(1, user.getRoles().size());
    }



}
