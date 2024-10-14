package b4u.pocketpartners.backend.groups.domain.model.commands;

public record AddGroupOperationCommand(Long groupId, Long expenseId, Long paymentId) {
}
