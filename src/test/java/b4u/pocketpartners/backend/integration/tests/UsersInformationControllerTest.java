package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import b4u.pocketpartners.backend.users.domain.services.UserInformationCommandService;
import b4u.pocketpartners.backend.users.domain.services.UserInformationQueryService;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.CreateUserInformationResource;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UpdateUserInformationResource;
import b4u.pocketpartners.backend.users.interfaces.rest.resources.UserInformationResource;
import b4u.pocketpartners.backend.users.interfaces.rest.transform.CreateUserInformationCommandFromResourceAssembler;
import b4u.pocketpartners.backend.users.interfaces.rest.transform.UpdateUserInformationCommandFromResourceAssembler;
import b4u.pocketpartners.backend.users.interfaces.rest.transform.UserInformationResourceFromEntityAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersInformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInformationQueryService userInformationQueryService;

    @MockBean
    private UserInformationCommandService userInformationCommandService;

    private UserInformation mockUserInformationEntity;

    @BeforeEach
    public void setUp() {
        // Initialize mock data or perform setup before each test
        mockUserInformationEntity = new UserInformation();
        //mockUserInformationEntity.setUser();

    }

    @Test
    public void testCreateUser() throws Exception {
        // Arrange
       // CreateUserInformationResource request = new CreateUserInformationResource();
        //request.setUsername("testuser");

        //when(userInformationCommandService.handle(any())).thenReturn(Optional.of(mockUserInformationEntity));

        // Act
        ResultActions resultActions = mockMvc.perform(post("/api/v1/usersInformation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"testuser\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testGetProfileById() throws Exception {
        // Arrange
        //when(userInformationQueryService.handle(any())).thenReturn(Optional.of(mockUserInformationEntity));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/usersInformation/{userInformationId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testUpdateUserInformationById() throws Exception {
        // Arrange
        //UpdateUserInformationResource request = new UpdateUserInformationResource();
        //request.setUsername("updateduser");

        //when(userInformationCommandService.handle(any())).thenReturn(Optional.of(mockUserInformationEntity));

        // Act
        ResultActions resultActions = mockMvc.perform(put("/api/v1/usersInformation/{userInformationId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"updateduser\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(jsonPath("$.username").value("updateduser"));
    }

    @Test
    public void testDeleteUserInformationById() throws Exception {
        // Arrange
        //when(userInformationCommandService.handle(any())).thenReturn(Optional.of(mockUserInformationEntity));

        // Act
        mockMvc.perform(delete("/api/v1/usersInformation/{userInformationId}", 1L))
                .andExpect(status().isNoContent());

        // No content to assert for deletion
    }
}
