package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.entities.Role;
import b4u.pocketpartners.backend.users.domain.model.valueobjects.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserTests {

    @Mock
    private Role mockAdminRole;

    @Mock
    private Role mockUserRole;

    @InjectMocks
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar mocks
        when(mockAdminRole.getName()).thenReturn(Roles.ROLE_ADMIN);
        when(mockUserRole.getName()).thenReturn(Roles.ROLE_USER);

        // Inicializar user con mocks
        user = new User("testUser", "securePassword123");
    }

    @Test
    void userInitializationWithUsernameAndPassword() {
        // Verifica que el usuario fue inicializado correctamente con nombre de usuario y contraseña
        assertEquals("testUser", user.getUsername());
        assertEquals("securePassword123", user.getPassword());
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    void userInitializationWithRoles() {
        // Act
        User userWithRoles = new User("roleUser", "password123", List.of(mockAdminRole, mockUserRole));

        // Assert
        assertEquals("roleUser", userWithRoles.getUsername());
        assertEquals("password123", userWithRoles.getPassword());
        assertEquals(2, userWithRoles.getRoles().size());
        assertTrue(userWithRoles.getRoles().contains(mockAdminRole));
        assertTrue(userWithRoles.getRoles().contains(mockUserRole));
    }

    @Test
    void addSingleRoleToUser() {
        // Act
        user.addRole(mockUserRole);

        // Assert
        assertTrue(user.getRoles().contains(mockUserRole));
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void addMultipleRolesToUser() {
        // Act
        user.addRoles(List.of(mockAdminRole, mockUserRole));

        // Assert
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(mockAdminRole));
        assertTrue(user.getRoles().contains(mockUserRole));
    }

    @Test
    void addDuplicateRolesToUser() {
        // Act
        user.addRole(mockAdminRole);
        user.addRole(mockAdminRole);  // Intentamos añadir el mismo rol de nuevo

        // Assert
        assertEquals(1, user.getRoles().size()); // El conjunto debería contener solo una instancia del rol
        assertTrue(user.getRoles().contains(mockAdminRole));
    }

    @Test
    void userInitializationWithDefaultConstructor() {
        // Act
        User defaultUser = new User();

        // Assert
        assertNotNull(defaultUser.getRoles());
        assertTrue(defaultUser.getRoles().isEmpty());
    }

    @Test
    void validateRoleSetWithEmptyList() {
        // Act
        List<Role> validatedRoles = Role.validateRoleSet(List.of());

        // Assert
        assertEquals(1, validatedRoles.size());
        assertEquals(Roles.ROLE_USER, validatedRoles.get(0).getName());
    }

    @Test
    void validateRoleSetWithNonEmptyList() {
        // Act
        List<Role> validatedRoles = Role.validateRoleSet(List.of(mockAdminRole));

        // Assert
        assertEquals(1, validatedRoles.size());
        assertEquals(mockAdminRole, validatedRoles.get(0));
    }
}