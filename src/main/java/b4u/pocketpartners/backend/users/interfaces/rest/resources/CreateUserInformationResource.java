package b4u.pocketpartners.backend.users.interfaces.rest.resources;

public record CreateUserInformationResource(String firstName,
                                            String lastName,
                                            String phoneNumber,
                                            String photo,
                                            String email,
                                            Long userId) {
}
