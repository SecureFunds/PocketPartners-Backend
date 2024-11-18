package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.*;
import b4u.pocketpartners.backend.groups.domain.model.queries.*;
import b4u.pocketpartners.backend.groups.interfaces.rest.GroupController;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.*;
import b4u.pocketpartners.backend.groups.domain.services.GroupCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class GroupControllerIT {

    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupCommandService groupCommandService;

    @Mock
    private GroupQueryService groupQueryService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateGroup() throws Exception {
        CreateGroupResource resource = new CreateGroupResource("Test Group", "http://example.com/photo.jpg", "Test Description", 1L);

        // Mock del servicio
        when(groupCommandService.handle(any(CreateGroupCommand.class))).thenReturn(1L);
        when(groupQueryService.handle(any(GetGroupByIdQuery.class))).thenReturn(Optional.of(new Group()));

        mockMvc.perform(post("/api/v1/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetGroupById() throws Exception {
        Long groupId = 1L;

        // Mock del servicio
        Group mockGroup = new Group(); // Asegúrate de inicializarlo con datos válidos
        when(groupQueryService.handle(any(GetGroupByIdQuery.class))).thenReturn(Optional.of(mockGroup));

        mockMvc.perform(get("/api/v1/groups/{groupId}", groupId))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllGroups() throws Exception {
        // Mock del servicio
        when(groupQueryService.handle(any(GetAllGroupsQuery.class))).thenReturn(List.of(new Group()));

        mockMvc.perform(get("/api/v1/groups"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateGroupImage() throws Exception {
        Long groupId = 1L;
        UpdateGroupImageResource resource = new UpdateGroupImageResource("http://example.com/new-image.jpg");

        // Mock del servicio
        when(groupCommandService.handle(any(UpdateGroupImageCommand.class))).thenReturn(Optional.of(new Group()));

        mockMvc.perform(put("/api/v1/groups/{groupId}/image", groupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateGroupDescriptionAndName() throws Exception {
        Long groupId = 1L;
        UpdateGroupResource resource = new UpdateGroupResource("Updated Name", "Updated Description");

        // Mock del servicio
        when(groupCommandService.handle(any(UpdateGroupCommand.class))).thenReturn(Optional.of(new Group()));

        mockMvc.perform(put("/api/v1/groups/{groupId}", groupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteGroup() throws Exception {
        Long groupId = 1L;

        // Mock del servicio
        doNothing().when(groupCommandService).handle(any(DeleteGroupCommand.class));

        mockMvc.perform(delete("/api/v1/groups/{groupId}", groupId))
                .andExpect(status().isOk());
    }

    @Test
    void testGenerateInvitation() throws Exception {
        Long groupId = 1L;

        // Mock del servicio
        when(groupCommandService.handle(any(GenerateInvitationCommand.class))).thenReturn("mocked-token");

        mockMvc.perform(post("/api/v1/groups/{groupId}/generate-invitation", groupId))
                .andExpect(status().isOk())
                .andExpect(content().string("mocked-token"));
    }
}