package b4u.pocketpartners.backend.groups.domain.model.queries;

public record GetGroupInvitationLinkQuery(
        Long groupId,
        Long adminId  
) {}