package b4u.pocketpartners.backend.groups.interfaces.rest.resources;

public record JoinGroupWithTokenResource(
        Long userId,
        String token
) {
}
