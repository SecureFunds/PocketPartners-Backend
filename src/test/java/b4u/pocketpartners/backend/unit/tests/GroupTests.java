package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupRole;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupTests {

    private Group group;

    @BeforeEach
    void setUp() {
        group = new Group("Test Group", "Description", "photo.jpg");
    }

    @Test
    void shouldInitializeGroupWithConstructor() {
        assertEquals("Test Group", group.getName());
        assertEquals("Description", group.getDescription());
        assertEquals("photo.jpg", group.getGroupPhoto());
        assertTrue(group.getMembers().isEmpty());
    }


    @Test
    void shouldUpdateGroupInformation() {
        group.updateInformation("Updated Group", "Updated Description");

        assertEquals("Updated Group", group.getName());
        assertEquals("Updated Description", group.getDescription());
    }

    @Test
    void shouldChangePhoto() {
        group.changePhoto("new_photo.jpg");

        assertEquals("new_photo.jpg", group.getGroupPhoto());
    }

    @Test
    void shouldGenerateInvitationToken() {
        group.generateInvitationToken();

        assertNotNull(group.getInvitationToken());
        assertFalse(group.getInvitationToken().isEmpty());
    }

    @Test
    void shouldValidateCorrectInvitationToken() {
        group.generateInvitationToken();
        String token = group.getInvitationToken();

        assertTrue(group.hasValidInvitationToken(token));
    }

    @Test
    void shouldInvalidateIncorrectInvitationToken() {
        group.generateInvitationToken();

        assertFalse(group.hasValidInvitationToken("invalid-token"));
    }

    @Test
    void shouldAddMemberToGroup() {
        GroupMember member = new GroupMember(group, null, GroupRole.MEMBER); // Simulando un miembro
        group.addMember(member);

        assertEquals(1, group.getMembers().size());
        assertTrue(group.getMembers().contains(member));
    }

    @Test
    void shouldGetAdminIdWhenAdminExists() {
        UserInformation adminUser = mock(UserInformation.class);
        when(adminUser.getId()).thenReturn(1L);

        GroupMember admin = new GroupMember(group, adminUser, GroupRole.ADMIN); // Ahora pasamos un UserInformation válido
        group.addMember(admin);

        assertEquals(1L, group.getAdminId());
    }

    @Test
    void shouldReturnNullWhenNoAdminExists() {
        GroupMember member = new GroupMember(group, null, GroupRole.MEMBER); // Miembro sin rol de administrador
        group.addMember(member);

        assertNull(group.getAdminId());
    }
}