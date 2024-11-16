package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.users.interfaces.rest.resources.RoleResource;
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
public class RolesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenRolesExist_whenGetAllRoles_thenReturnListOfRoles() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/ap/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(not(empty()))))
                .andExpect(jsonPath("$[0].name", notNullValue()))
                .andExpect(jsonPath("$[0].id", notNullValue()));
    }

    @Test
    void givenNoRolesExist_whenGetAllRoles_thenReturnEmptyList() throws Exception {

        // Act & Assert
        mockMvc.perform(get("/ap/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(empty())));
    }
}