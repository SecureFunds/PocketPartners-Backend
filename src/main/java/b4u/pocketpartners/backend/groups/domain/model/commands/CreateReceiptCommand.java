package b4u.pocketpartners.backend.groups.domain.model.commands;

public record CreateReceiptCommand(String name, String description, String photo, Long groupId, Long userId) {
}
