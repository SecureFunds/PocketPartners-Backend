package b4u.pocketpartners.backend.groups.domain.model.commands;

import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Currencies;

import java.util.Set;

public record CreateGroupCommand(String name, String groupPhoto, Set<Currencies> currency) {
    public CreateGroupCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name cannot be null or empty");
        }
        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Group must have at least one currency");
        }
    }

    public Iterable<Currencies> getCurrency() {
        return currency;
    }

}