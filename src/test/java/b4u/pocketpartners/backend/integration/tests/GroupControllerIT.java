package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.DeleteGroupCommand;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.CreateGroupResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.UpdateGroupImageResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.UpdateGroupResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
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

    @MockBean
    private GroupCommandService groupCommandService;

    @MockBean
    private GroupQueryService groupQueryService;

    @Test
    void shouldCreateGroupSuccessfully() throws Exception {
        CreateGroupResource createGroupResource = new CreateGroupResource("Test Group", "photo.jpg", "Description", 1L);
        Group group = new Group("Test Group", "Description", "photo.jpg");
        ReflectionTestUtils.setField(group, "id", 1L);

        when(groupCommandService.handle(any(CreateGroupCommand.class))).thenReturn(1L);
        when(groupQueryService.handle(any(GetGroupByIdQuery.class))).thenReturn(Optional.of(group));

        mockMvc.perform(post("/api/v1/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGroupResource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Group"));
    }

    @Test
    void shouldGetGroupByIdSuccessfully() throws Exception {
        Group group = new Group("Test Group", "Description", "photo.jpg");
        ReflectionTestUtils.setField(group, "id", 1L);

        when(groupQueryService.handle(any(GetGroupByIdQuery.class))).thenReturn(Optional.of(group));

        mockMvc.perform(get("/api/v1/groups/{groupId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Group"));
    }

    @Test
    void shouldGetAllGroupsSuccessfully() throws Exception {
        Group group1 = new Group("Group 1", "Description 1", "photo1.jpg");
        Group group2 = new Group("Group 2", "Description 2", "photo2.jpg");

        when(groupQueryService.handle(any(GetAllGroupsQuery.class))).thenReturn(List.of(group1, group2));

        mockMvc.perform(get("/api/v1/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Group 1"))
                .andExpect(jsonPath("$[1].name").value("Group 2"));
    }

    @Test
    void shouldDeleteGroupSuccessfully() throws Exception {
        doNothing().when(groupCommandService).handle(any(DeleteGroupCommand.class));

        mockMvc.perform(delete("/api/v1/groups/{groupId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Group with given id successfully deleted"));
    }
}