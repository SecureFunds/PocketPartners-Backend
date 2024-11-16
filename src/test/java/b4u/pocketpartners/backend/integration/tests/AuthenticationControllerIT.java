package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.users.interfaces.rest.resources.SignInResource;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.SignUpResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenValidSignInRequest_whenSignIn_thenReturnAuthenticatedUser() throws Exception {
        // Arrange
        SignInResource signInResource = new SignInResource("validUser1", "validPassword");

        // Act & Assert
        mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signInResource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.user.username", is("validUser")));
    }

    @Test
    void givenInvalidSignInRequest_whenSignIn_thenReturnNotFound() throws Exception {
        // Arrange
        SignInResource signInResource = new SignInResource("invalidUser", "wrongPassword");

        // Act & Assert
        mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signInResource)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidSignUpRequest_whenSignUp_thenReturnCreatedUser() throws Exception {
        // Arrange
        SignUpResource signUpResource = new SignUpResource(
                "newUser",
                "newPassword",
                List.of("ROLE_USER")
        );

        // Act & Assert
        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpResource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("newUser")));
    }

    @Test
    void givenInvalidSignUpRequest_whenSignUp_thenReturnBadRequest() throws Exception {
        // Arrange
        SignUpResource signUpResource = new SignUpResource(
                "newUser",
                "newPassword",
                List.of("ROLE_USER")
        );

        // Act & Assert
        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpResource)))
                .andExpect(status().isBadRequest());
    }

    // Helper method to convert objects to JSON strings
    private static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
