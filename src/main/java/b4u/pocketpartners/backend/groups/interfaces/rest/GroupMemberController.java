package b4u.pocketpartners.backend.groups.interfaces.rest;

import b4u.pocketpartners.backend.groups.domain.model.commands.AddMemberCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.GenerateInvitationCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.JoinGroupWithTokenCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.RemoveMemberCommand;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetALLGroupByUserIdQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllMembersInGroupQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupMemberCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupMemberQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.AddMemberResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.GroupMemberResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.JoinGroupWithTokenResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.AddMemberCommandFromResourceAssembler;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.CreateGroupCommandFromResourceAssembler;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.GroupMemberResourceFromEntityAssembler;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.GroupResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/groups",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Member Group", description = "Member Group Management Endpoints")
public class GroupMemberController {

    private final GroupMemberCommandService groupMemberCommandService;
    private final GroupMemberQueryService groupMemberQueryService;

    public GroupMemberController(GroupMemberCommandService groupMemberCommandService, GroupMemberQueryService groupMemberQueryService) {
        this.groupMemberCommandService = groupMemberCommandService;
        this.groupMemberQueryService = groupMemberQueryService;
    }

    @Operation(summary = "Get all groups by user ID", description = "Retrieves all groups associated with the specified user ID.")
    @GetMapping("/members/{userId}")
    public ResponseEntity<List<GroupMemberResource>> getGroupsByUserId(@PathVariable Long userId) {
        var groups = groupMemberQueryService.handle(new GetALLGroupByUserIdQuery(userId));
        if (groups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var groupMemberResources = groups.stream()
                .map(GroupMemberResourceFromEntityAssembler::fromEntityToResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupMemberResources);
    }

    @Operation(summary = "Add member to group")
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupMemberResource> addMember( @PathVariable Long groupId, @PathVariable Long userId) {
        var addMemberCommand = new AddMemberCommand(groupId, userId);
        var addedMember = groupMemberCommandService.handle(addMemberCommand);

        if (addedMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        var groupMemberResource = GroupMemberResourceFromEntityAssembler.fromEntityToResource(addedMember.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(groupMemberResource);
    }

    @Operation(summary = "Remove member from group")
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<String> removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
        var removeMemberCommand = new RemoveMemberCommand(groupId, userId);
        groupMemberCommandService.handle(removeMemberCommand);
        return ResponseEntity.ok("Member removed from group successfully");
    }



    @Operation(summary = "Get all members of a group")
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMemberResource>> getGroupMembers(@PathVariable Long groupId) {
        var getAllMembersInGroupQuery = new GetAllMembersInGroupQuery(groupId);
        var members = groupMemberQueryService.handle(getAllMembersInGroupQuery);

        if (members.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var memberResources = members.stream()
                .map(GroupMemberResourceFromEntityAssembler::fromEntityToResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberResources);
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<String> joinGroupWithToken(@PathVariable Long groupId, @RequestBody JoinGroupWithTokenResource joinGroupWithTokenResource  ) {
        var command = new JoinGroupWithTokenCommand(groupId, joinGroupWithTokenResource.token(), joinGroupWithTokenResource.userId());
        groupMemberCommandService.handle(command);
        return ResponseEntity.ok("User successfully joined the group");
    }



}