package b4u.pocketpartners.backend.groups.interfaces.rest;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllGroupsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetGroupByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.GroupCommandService;
import b4u.pocketpartners.backend.groups.domain.services.GroupQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.CreateGroupResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.GroupResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.CreateGroupCommandFromResourceAssembler;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.GroupResourceFromEntityAssembler;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Group", description = "Group Management Endpoints")
public class GroupController {

    private final GroupCommandService groupCommandService;
    private final GroupQueryService groupQueryService;

    public GroupController(GroupCommandService groupCommandService, GroupQueryService groupQueryService) {
        this.groupCommandService = groupCommandService;
        this.groupQueryService = groupQueryService;
    }

    /**
     * Handles the POST request to create a new group.
     *
     * @param createGroupResource The request body containing the details of the group to be created.
     * @return ResponseEntity<Group> Returns a ResponseEntity containing the created Group and HTTP status.
     * If the group creation is successful, it returns the created group with HTTP status 201 (Created).
     * If the group creation fails, it returns HTTP status 400 (Bad Request).
     */
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody CreateGroupResource createGroupResource) {
        var createGroupCommand = CreateGroupCommandFromResourceAssembler.toCommandFromResource(createGroupResource);
        var group = groupCommandService.handle(createGroupCommand);
        return group.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Handles the GET request to retrieve a group by its ID.
     *
     * @param groupId The ID of the group to be retrieved.
     * @return ResponseEntity<GroupResource> Returns a ResponseEntity containing the retrieved GroupResource and HTTP status.
     * If the group is found, it returns the group with HTTP status 200 (OK).
     * If the group is not found, it returns HTTP status 400 (Bad Request).
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResource> getGroup(@PathVariable Long groupId) {
        var getGroupByIdQuery = new GetGroupByIdQuery(groupId);
        var group = groupQueryService.handle(getGroupByIdQuery);
        if (group.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var courseResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(courseResource);
    }

    //GET ALL
    @Operation(summary = "Get all groups")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of groups")
    })
    @GetMapping
    public ResponseEntity<List<GroupResource>> getAllGroups() {
        var getAllGroupsQuery = new GetAllGroupsQuery();
        var groups = groupQueryService.handle(getAllGroupsQuery);
        var groupsResources = groups.stream().map(GroupResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(groupsResources);
    }
}
