package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.domain.model.entities.Currency;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupName;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupPhoto;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Currencies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GroupTests {

    @Mock
    private CreateGroupCommand mockCreateGroupCommand;

    private Group group;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        group = new Group(new GroupName("Test Group"), new GroupPhoto("test_photo.jpg"));
    }

    @Test
    void createGroupFromCommand() {
        // Mocking CreateGroupCommand
        when(mockCreateGroupCommand.name()).thenReturn("Grupo 4");
        when(mockCreateGroupCommand.groupPhoto()).thenReturn("test_foto.jpg");
        when(mockCreateGroupCommand.currency()).thenReturn(Collections.singleton(Currencies.USD));

        // Creating Group from command
        Group group = new Group(mockCreateGroupCommand);

        // Assertions
        assertNotNull(group);
        assertEquals("Grupo 4", group.getName());
        assertEquals("test_foto.jpg", group.getGroupPhoto());
        assertEquals(1, group.getCurrencies().size());

        // Adjusted assertion for currency code
        Currency currency = group.getCurrencies().iterator().next();
        assertEquals(Currencies.USD.name(), currency.getCode());
    }

    @Test
    void changeGroupName() {
        // Act
        group.changeName(new GroupName("New Name"));

        // Assert
        assertEquals("New Name", group.getName());
    }

    @Test
    void changeGroupPhoto() {
        // Act
        group.changeGroupPhoto(new GroupPhoto("new_photo.jpg"));

        // Assert
        assertEquals("new_photo.jpg", group.getGroupPhoto());
    }

    @Test
    void defaultConstructorShouldInitializeWithDefaults() {
        // Arrange + Act
        Group defaultGroup = new Group();

        // Assert
        assertNotNull(defaultGroup);
        assertEquals("No name", defaultGroup.getName());
        assertEquals("No link", defaultGroup.getGroupPhoto());
        assertEquals(1, defaultGroup.getCurrencies().size()); // Assuming it defaults to one currency
    }

}
