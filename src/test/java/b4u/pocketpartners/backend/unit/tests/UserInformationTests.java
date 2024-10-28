package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import b4u.pocketpartners.backend.users.domain.model.commands.CreateUserInformationCommand;
import b4u.pocketpartners.backend.users.domain.model.valueobjects.EmailAddress;
import b4u.pocketpartners.backend.users.domain.model.valueobjects.PersonName;
import b4u.pocketpartners.backend.users.domain.model.valueobjects.PhoneNumber;
import b4u.pocketpartners.backend.users.domain.model.valueobjects.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class UserInformationTests {

    @Mock
    private User mockUser;


    private UserInformation userInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración del mock
        when(mockUser.getUsername()).thenReturn("testUser");

        // Crear una instancia de UserInformation usando el constructor con parámetros válidos
        userInfo = new UserInformation("John", "Doe", "123456789", "photo.jpg", "john.doe@example.com", mockUser);
    }

    @Test
    void userInformationInitializationWithAttributes() {
        // Verifica que UserInformation se inicializa correctamente con atributos específicos
        assertEquals("John Doe", userInfo.getFullName());
        assertEquals("123456789", userInfo.getPhoneNumber());
        assertEquals("photo.jpg", userInfo.getPhoto());
        assertEquals("john.doe@example.com", userInfo.getEmailAddress());
        assertEquals(mockUser, userInfo.getUser());
    }

    @Test
    void userInformationInitializationWithCommand() {
        // Arrange
        Long userId = userInfo.getId(); // Se puede simular con el mock si es necesario
        CreateUserInformationCommand command = new CreateUserInformationCommand("Jane", "Smith", "987654321", "profile.jpg", "jane.smith@example.com", userId);

        // Act
        UserInformation userInfoFromCommand = new UserInformation(command);

        // Assert
        assertEquals("Jane Smith", userInfoFromCommand.getFullName());
        assertEquals("987654321", userInfoFromCommand.getPhoneNumber());
        assertEquals("profile.jpg", userInfoFromCommand.getPhoto());
        assertEquals("jane.smith@example.com", userInfoFromCommand.getEmailAddress());
    }

    @Test
    void updateName() {
        // Act
        userInfo.updateName("Mike", "Johnson");

        // Assert
        assertEquals("Mike Johnson", userInfo.getFullName());
    }

    @Test
    void updatePhoneNumber() {
        // Act
        userInfo.updatePhoneNumber("555123456");

        // Assert
        assertEquals("555123456", userInfo.getPhoneNumber());
    }

    @Test
    void updatePhoto() {
        // Act
        userInfo.updatePhoto("newphoto.jpg");

        // Assert
        assertEquals("newphoto.jpg", userInfo.getPhoto());
    }

    @Test
    void updateEmail() {
        // Act
        userInfo.updateEmail("new.email@example.com");

        // Assert
        assertEquals("new.email@example.com", userInfo.getEmailAddress());
    }


    @Test
    void setNameDirectly() {
        // Arrange
        PersonName newName = new PersonName("Alice", "Wonderland");

        // Act
        userInfo.setName(newName);

        // Assert
        assertEquals("Alice Wonderland", userInfo.getFullName());
    }

    @Test
    void setEmailDirectly() {
        // Arrange
        EmailAddress newEmail = new EmailAddress("alice@example.com");

        // Act
        userInfo.setEmail(newEmail);

        // Assert
        assertEquals("alice@example.com", userInfo.getEmailAddress());
    }
}