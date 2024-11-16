package b4u.pocketpartners.backend.integration.tests;


import b4u.pocketpartners.backend.groups.domain.model.commands.AddMemberCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.JoinGroupWithTokenCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.RemoveMemberCommand;
import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetALLGroupByUserIdQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllMembersInGroupQuery;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupRole;
import b4u.pocketpartners.backend.groups.domain.services.GroupMemberCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupMemberQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.JoinGroupWithTokenResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GroupMemberControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupMemberCommandService groupMemberCommandService;

    @MockBean
    private GroupMemberQueryService groupMemberQueryService;

    @Test
    void shouldGetGroupsByUserIdSuccessfully() throws Exception {
        GroupMember member = new GroupMember();
        ReflectionTestUtils.setField(member, "role", GroupRole.MEMBER);
        ReflectionTestUtils.setField(member, "joinedAt", new Date());

        when(groupMemberQueryService.handle(any(GetALLGroupByUserIdQuery.class))).thenReturn(List.of(member));

        mockMvc.perform(get("/api/v1/groups/members/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].role").value("MEMBER"));
    }

    @Test
    void shouldAddMemberSuccessfully() throws Exception {
        GroupMember member = new GroupMember();
        ReflectionTestUtils.setField(member, "role", GroupRole.MEMBER);
        ReflectionTestUtils.setField(member, "joinedAt", new Date());

        when(groupMemberCommandService.handle(any(AddMemberCommand.class))).thenReturn(Optional.of(member));

        mockMvc.perform(post("/api/v1/groups/{groupId}/members/{userId}", 1L, 2L))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("MEMBER"));
    }

    @Test
    void shouldRemoveMemberSuccessfully() throws Exception {
        doNothing().when(groupMemberCommandService).handle(any(RemoveMemberCommand.class));

        mockMvc.perform(delete("/api/v1/groups/{groupId}/members/{userId}", 1L, 2L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetAllMembersInGroupSuccessfully() throws Exception {
        GroupMember member = new GroupMember();
        ReflectionTestUtils.setField(member, "role", GroupRole.MEMBER);
        ReflectionTestUtils.setField(member, "joinedAt", new Date());

        when(groupMemberQueryService.handle(any(GetAllMembersInGroupQuery.class))).thenReturn(List.of(member));

        mockMvc.perform(get("/api/v1/groups/{groupId}/members", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].role").value("MEMBER"));
    }

    @Test
    void shouldJoinGroupWithTokenSuccessfully() throws Exception {
        JoinGroupWithTokenResource resource = new JoinGroupWithTokenResource(2L, "valid-token");
        GroupMember member = new GroupMember();
        ReflectionTestUtils.setField(member, "role", GroupRole.MEMBER);
        ReflectionTestUtils.setField(member, "joinedAt", new Date());

        when(groupMemberCommandService.handle(any(JoinGroupWithTokenCommand.class))).thenReturn(Optional.of(member));

        mockMvc.perform(post("/api/v1/groups/{groupId}/join", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("MEMBER"));
    }
}