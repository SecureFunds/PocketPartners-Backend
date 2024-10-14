package b4u.pocketpartners.backend.groups.interfaces.rest;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupMemberCommand;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsOfUserByUserInformationId;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllMembersInGroupQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupMemberCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupMemberQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.GroupMemberResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.GroupMemberResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/v1/groups")
@Tag(name = "Member Group", description = "Member Group Management Endpoints")
public class GroupMemberController {
    private final GroupMemberCommandService groupMemberCommand;
    private final GroupMemberQueryService groupMemberQuery;

    public GroupMemberController(GroupMemberCommandService groupMemberCommandService, GroupMemberQueryService groupMemberQuery) {
        this.groupMemberCommand = groupMemberCommandService;
        this.groupMemberQuery = groupMemberQuery;
    }

    @GetMapping("members/{userId}")
        public ResponseEntity<List<GroupMemberResource>> getGroupsByUserId(@PathVariable Long userId) {
        var groups = groupMemberQuery.handle(new GetAllGroupsOfUserByUserInformationId(userId));
        if (groups.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var groupMemberResources = groups.stream().map(GroupMemberResourceFromEntityAssembler::fromEntityToResource);
        return ResponseEntity.ok(groupMemberResources.toList());
    }

    @PostMapping("{groupId}/members/{userId}")
    public ResponseEntity<GroupMemberResource> joinGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        var createGroupMemberCommand = new CreateGroupMemberCommand(groupId, userId);
        var groupMember = groupMemberCommand.handle(createGroupMemberCommand);
        if (groupMember.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var groupMemberResource = GroupMemberResourceFromEntityAssembler.fromEntityToResource(groupMember.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(groupMemberResource);
    }

    @GetMapping("{groupId}/members")
    public ResponseEntity<List<GroupMemberResource>> getGroupMembers(@PathVariable Long groupId) {
        var groupMembers = groupMemberQuery.handle(new GetAllMembersInGroupQuery(groupId));
        if (groupMembers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var groupMemberResources = groupMembers.stream().map(GroupMemberResourceFromEntityAssembler::fromEntityToResource);
        return ResponseEntity.ok(groupMemberResources.toList());
    }

}
