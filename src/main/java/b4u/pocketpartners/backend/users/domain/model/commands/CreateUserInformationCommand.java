package b4u.pocketpartners.backend.users.domain.model.commands;

public record CreateUserInformationCommand(String firstName, String lastName, String phoneNumber, String photo, String email, Long userId) {
}