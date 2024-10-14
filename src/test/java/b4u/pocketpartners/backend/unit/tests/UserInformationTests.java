package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import b4u.pocketpartners.backend.users.domain.model.commands.CreateUserInformationCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserInformationTests {

    private User user;
    private UserInformation userInformation;

    @BeforeEach
    public void setUp() {
        user = new User();
        userInformation = new UserInformation("John", "Doe", "1234567890", "photoUrl", "john.doe@example.com", user);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("John Doe", userInformation.getFullName());
        assertEquals("1234567890", userInformation.getPhoneNumber());
        assertEquals("photoUrl", userInformation.getPhoto());
        assertEquals("john.doe@example.com", userInformation.getEmailAddress());
        assertEquals(user, userInformation.getUser());
    }

    @Test
    public void testUpdateName() {
        userInformation.updateName("Jane", "Smith");
        assertEquals("Jane Smith", userInformation.getFullName());
    }

    @Test
    public void testUpdatePhoneNumber() {
        userInformation.updatePhoneNumber("0987654321");
        assertEquals("0987654321", userInformation.getPhoneNumber());
    }

    @Test
    public void testUpdatePhoto() {
        userInformation.updatePhoto("newPhotoUrl");
        assertEquals("newPhotoUrl", userInformation.getPhoto());
    }

    @Test
    public void testUpdateEmail() {
        userInformation.updateEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", userInformation.getEmailAddress());
    }

    @Test
    public void testConstructorWithCommand() {
        CreateUserInformationCommand command = new CreateUserInformationCommand("Alice", "Johnson", "123456789", "photoLink", "alice.johnson@example.com", 1L);
        UserInformation userInfoFromCommand = new UserInformation(command);

        assertEquals("Alice Johnson", userInfoFromCommand.getFullName());
        assertEquals("123456789", userInfoFromCommand.getPhoneNumber());
        assertEquals("photoLink", userInfoFromCommand.getPhoto());
        assertEquals("alice.johnson@example.com", userInfoFromCommand.getEmailAddress());
        assertNotNull(userInfoFromCommand.getUser()); // Assuming a new User is created in the constructor
    }
}
