package b4u.pocketpartners.backend.groups.interfaces.rest;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.commands.DeleteGroupCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.GenerateInvitationCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.JoinGroupWithTokenCommand;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsByUserIdQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.CreateGroupResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.GroupResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.UpdateGroupResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.CreateGroupCommandFromResourceAssembler;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.GroupResourceFromEntityAssembler;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.UpdateGroupCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping(value = "api/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Group", description = "Group Management Endpoints")
public class GroupController {

    private final GroupCommandService groupCommandService;
    private final GroupQueryService groupQueryService;

    public GroupController(GroupCommandService groupCommandService, GroupQueryService groupQueryService) {
        this.groupCommandService = groupCommandService;
        this.groupQueryService = groupQueryService;
    }

    @Operation(summary = "Create a new group")
    @PostMapping
    public ResponseEntity<GroupResource> createGroup(@RequestBody CreateGroupResource createGroupResource) {
        var createGroupCommand = CreateGroupCommandFromResourceAssembler.toCommandFromResource(createGroupResource);
        var groupId = groupCommandService.handle(createGroupCommand);
        if (groupId == 0L)  return ResponseEntity.badRequest().build();
        var getGroupByIdQuery = new GetGroupByIdQuery(groupId);
        var group = groupQueryService.handle(getGroupByIdQuery);
        if(group.isEmpty()) return ResponseEntity.badRequest().build();
        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return new ResponseEntity<>(groupResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Get by group Id")
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResource> getGroupById(@PathVariable Long groupId) {
        var getGroupByIdQuery = new GetGroupByIdQuery(groupId);
        var group = groupQueryService.handle(getGroupByIdQuery);
        if (group.isEmpty()) return ResponseEntity.badRequest().build();
        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResource);
    }

    @Operation(summary = "Get all groups")
    @GetMapping
    public ResponseEntity<List<GroupResource>> getAllGroups() {
        var getAllGroupsQuery = new GetAllGroupsQuery();
        var groups = groupQueryService.handle(getAllGroupsQuery);
        var groupResources = groups.stream().map(GroupResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(groupResources);
    }

    @Operation(summary = "Update by group ID")
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupResource> updateGroup(@PathVariable Long groupId, @RequestBody UpdateGroupResource updateGroupResource) {
        var updateGroupCommand = UpdateGroupCommandFromResourceAssembler.toCommandFromResource(groupId, updateGroupResource);
        var updatedGroup = groupCommandService.handle(updateGroupCommand);
        if (updatedGroup.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(updatedGroup.get());
        return ResponseEntity.ok(groupResource);
    }

    @Operation(summary = "Delete by group ID")
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {
        var deleteGroupCommand = new DeleteGroupCommand(groupId);
        groupCommandService.handle(deleteGroupCommand);
        return ResponseEntity.ok("Group with given id successfully deleted");
    }

    @PostMapping("/{groupId}/generate-invitation")
    public ResponseEntity<String> generateInvitation(@PathVariable Long groupId) {
        var command = new GenerateInvitationCommand(groupId);
        var token = groupCommandService.handle(command);
        return ResponseEntity.ok(token);
    }




}
