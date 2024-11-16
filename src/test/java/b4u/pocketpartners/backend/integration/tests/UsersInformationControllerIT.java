package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.users.interfaces.rest.resources.CreateUserInformationResource;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UpdateUserInformationResource;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UserInformationResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersInformationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenValidData_whenCreateUserInformation_thenReturnCreated() throws Exception {
        // Arrange
        CreateUserInformationResource resource = new CreateUserInformationResource(
                "John",
                "Doe",
                "+123456789",
                "john.doe@example.com",
                "photo_url",
                1L // Agregar el userId requerido
        );

        // Act & Assert
        mockMvc.perform(post("/api/v1/usersInformation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
    }

    @Test
    void givenInvalidData_whenCreateUserInformation_thenReturnBadRequest() throws Exception {
        // Arrange
        CreateUserInformationResource resource = new CreateUserInformationResource(
                "", // Invalid first name
                "Doe",
                "+123456789",
                "john.doe@example.com",
                "photo_url",
                1L
        );

        // Act & Assert
        mockMvc.perform(post("/api/v1/usersInformation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenExistingUserInformationId_whenGetUserInformationById_thenReturnUserInformation() throws Exception {
        // Arrange
        Long userInformationId = 1L; // Ensure this ID exists in the database

        // Act & Assert
        mockMvc.perform(get("/api/v1/usersInformation/{id}", userInformationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void givenNonExistingUserInformationId_whenGetUserInformationById_thenReturnNotFound() throws Exception {
        // Arrange
        Long nonExistingId = 999L;

        // Act & Assert
        mockMvc.perform(get("/api/v1/usersInformation/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenExistingUserInformation_whenGetAllUsersInformation_thenReturnList() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/usersInformation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))));
    }

    @Test
    void givenValidData_whenUpdateUserInformation_thenReturnUpdated() throws Exception {
        // Arrange
        Long userInformationId = 1L; // Ensure this ID exists in the database
        UpdateUserInformationResource resource = new UpdateUserInformationResource(
                "Updated First Name",
                "Updated Last Name",
                "+987654321",
                "updated.email@example.com",
                "updated_photo_url"
        );

        // Act & Assert
        mockMvc.perform(put("/api/v1/usersInformation/{id}", userInformationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is("Updated First Name Updated Last Name")))
                .andExpect(jsonPath("$.email", is("updated.email@example.com")));
    }

    @Test
    void givenNonExistingUserInformation_whenUpdateUserInformation_thenReturnNotFound() throws Exception {
        // Arrange
        Long nonExistingId = 999L;
        UpdateUserInformationResource resource = new UpdateUserInformationResource(
                "Updated First Name",
                "Updated Last Name",
                "+987654321",
                "updated.email@example.com",
                "updated_photo_url"
        );

        // Act & Assert
        mockMvc.perform(put("/api/v1/usersInformation/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resource)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenExistingUserInformationId_whenDeleteUserInformation_thenReturnNoContent() throws Exception {
        // Arrange
        Long userInformationId = 1L; // Ensure this ID exists in the database

        // Act & Assert
        mockMvc.perform(delete("/api/v1/usersInformation/{id}", userInformationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenNonExistingUserInformationId_whenDeleteUserInformation_thenReturnNotFound() throws Exception {
        // Arrange
        Long nonExistingId = 999L;

        // Act & Assert
        mockMvc.perform(delete("/api/v1/usersInformation/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Helper method to convert objects to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
