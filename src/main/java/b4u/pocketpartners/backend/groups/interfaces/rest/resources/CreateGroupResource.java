package b4u.pocketpartners.backend.groups.interfaces.rest.resources;

import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Currencies;

import java.util.Set;

public record CreateGroupResource(String name, String groupPhoto, Set<Currencies> currency) {
}