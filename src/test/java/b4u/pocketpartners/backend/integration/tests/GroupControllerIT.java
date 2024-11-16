package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.*;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.CreateGroupResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.UpdateGroupImageResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.UpdateGroupResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GroupControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupCommandService groupCommandService;

    @Autowired
    private GroupQueryService groupQueryService;
/*
    @Test
    void shouldCreateGroupSuccessfully() throws Exception {
        // Arrange
        CreateGroupResource createGroupResource = new CreateGroupResource("New Group", "photo.jpg", "A new group", 1L);
        Group group = new Group("New Group", "A new group", "photo.jpg");
        group.setId(1L);

        when(groupCommandService.handle(any(CreateGroupCommand.class))).thenReturn(1L);
        when(groupQueryService.handle(any(GetGroupByIdQuery.class))).thenReturn(Optional.of(group));

        // Act & Assert
        mockMvc.perform(post("/api/v1/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGroupResource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Group"))
                .andExpect(jsonPath("$.description").value("A new group"))
                .andExpect(jsonPath("$.groupPhoto").value("photo.jpg"));
    }

 */
    /*

    @Test
    void shouldGetGroupByIdSuccessfully() throws Exception {
        // Arrange
        Group group = new Group("Test Group", "Test Description", "test.jpg");
        group.setId(1L);

        when(groupQueryService.handle(any(GetGroupByIdQuery.class))).thenReturn(Optional.of(group));

        // Act & Assert
        mockMvc.perform(get("/api/v1/groups/{groupId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Group"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.groupPhoto").value("test.jpg"));
    }

     */

    @Test
    void shouldGetAllGroupsSuccessfully() throws Exception {
        // Arrange
        Group group1 = new Group("Group 1", "Description 1", "photo1.jpg");
        Group group2 = new Group("Group 2", "Description 2", "photo2.jpg");

        when(groupQueryService.handle(any(GetAllGroupsQuery.class))).thenReturn(List.of(group1, group2));

        // Act & Assert
        mockMvc.perform(get("/api/v1/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Group 1"))
                .andExpect(jsonPath("$[1].name").value("Group 2"));
    }
/*
    @Test
    void shouldUpdateGroupImageSuccessfully() throws Exception {
        // Arrange
        UpdateGroupImageResource resource = new UpdateGroupImageResource("newPhoto.jpg");
        Group updatedGroup = new Group("Updated Group", "Description", "newPhoto.jpg");
        updatedGroup.setId(1L);

        when(groupCommandService.handle(any(UpdateGroupImageCommand.class))).thenReturn(Optional.of(updatedGroup));

        // Act & Assert
        mockMvc.perform(put("/api/v1/groups/{groupId}/image", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupPhoto").value("newPhoto.jpg"));
    }

 */

    @Test
    void shouldDeleteGroupSuccessfully() throws Exception {
        // Arrange
        doNothing().when(groupCommandService).handle(any(DeleteGroupCommand.class));

        // Act & Assert
        mockMvc.perform(delete("/api/v1/groups/{groupId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Group with given id successfully deleted"));
    }
}