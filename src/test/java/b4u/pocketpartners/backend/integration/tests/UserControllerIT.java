package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.users.interfaces.rest.UsersController;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UserResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenNoUsers_whenGetAllUsers_thenReturnEmptyList() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0))); // La respuesta debe ser una lista vacía
    }

    @Test
    void givenUsersExist_whenGetAllUsers_thenReturnListOfUsers() throws Exception {
        // Arrange: Crear usuarios directamente en la base de datos (si estás usando una DB en memoria como H2)
        UserResource user1 = new UserResource(1L, "john_doe", List.of("ROLE_USER"));
        UserResource user2 = new UserResource(2L, "jane_doe", List.of("ROLE_ADMIN", "ROLE_USER"));
        // Puedes usar servicios o scripts para agregar usuarios si es necesario.

        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Esperamos 2 usuarios
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("john_doe")))
                .andExpect(jsonPath("$[0].roles", hasSize(1)))
                .andExpect(jsonPath("$[0].roles[0]", is("ROLE_USER")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("jane_doe")))
                .andExpect(jsonPath("$[1].roles", hasSize(2)))
                .andExpect(jsonPath("$[1].roles", containsInAnyOrder("ROLE_ADMIN", "ROLE_USER")));
    }

    @Test
    void givenUserExists_whenGetUserById_thenReturnUser() throws Exception {
        // Arrange: Crear un usuario específico en la base de datos
        Long userId = 1L;
        UserResource user = new UserResource(userId, "john_doe", List.of("ROLE_USER"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("john_doe")))
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(jsonPath("$.roles[0]", is("ROLE_USER")));
    }

    @Test
    void givenUserDoesNotExist_whenGetUserById_thenReturnNotFound() throws Exception {
        // Arrange: Simular que el usuario no existe
        Long userId = 999L;

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // La respuesta debe ser 404
    }
}