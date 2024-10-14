package b4u.pocketpartners.backend.groups.domain.model.aggregates;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.domain.model.entities.Currency;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupName;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupPhoto;
import b4u.pocketpartners.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Group class represents a group entity in the system.
 * It extends the AuditableAbstractAggregateRoot class, which provides audit fields for the entity.
 */
@Getter
@Entity
@Table(name = "pocket_groups")
public class Group extends AuditableAbstractAggregateRoot<Group> {

    /**
     * The name of the group, represented as an embedded value object.
     */
    @Embedded
    private GroupName name;

    /**
     * The photo of the group, represented as an embedded value object.
     */
    @Embedded
    private GroupPhoto groupPhoto;

    /**
     * The currencies associated with the group, represented as a Set of Currency entities.
     * This is a many-to-many relationship, managed with a join table.
     * -- GETTER --
     *  Returns the currencies associated with the group.
     *
     * @return The currencies associated with the group.

     */
    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "group_currencies",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "currency_id")
    )
    private Set<Currency> currencies;

    /**
     * Constructs a new Group with the provided name and photo.
     *
     * @param name The name of the group.
     * @param groupPhoto The photo of the group.
     */
    public Group(GroupName name, GroupPhoto groupPhoto) {
        this.name = name;
        this.groupPhoto = groupPhoto;
    }

    /**
     * Constructs a new Group from a CreateGroupCommand.
     *
     * @param command The command containing the details of the group to be created.
     */
    public Group(CreateGroupCommand command) {
        this.name = new GroupName(command.name());
        this.groupPhoto = new GroupPhoto(command.groupPhoto());
        this.currencies = command.currency().stream()
                .map(Currency::new)
                .collect(Collectors.toSet());
    }

    /**
     * Default constructor. Constructs a new Group with a default name and photo.
     */
    public Group() {
        this.name = new GroupName();
        this.groupPhoto = new GroupPhoto();
        this.currencies = Set.of(Currency.getDefaultRole());
    }

    public Group(String name, String s, Currency currency) {
        this.name = new GroupName(name);
        this.groupPhoto = new GroupPhoto(s);
        this.currencies = Set.of(currency);
    }

    /**
     * Changes the name of the group.
     *
     * @param name The new name of the group.
     */
    public void changeName(GroupName name) {
        this.name = name;
    }

    /**
     * Changes the photo of the group.
     *
     * @param groupPhoto The new photo of the group.
     */
    public void changeGroupPhoto(GroupPhoto groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    /**
     * Changes the name of the group.
     *
     * @param name The new name of the group, as a string.
     */
    public void changeName(String name) {
        this.name = new GroupName(name);
    }

    /**
     * Changes the photo of the group.
     *
     * @param groupPhoto The new photo of the group, as a string.
     */
    public void changeGroupPhoto(String groupPhoto) {
        this.groupPhoto = new GroupPhoto(groupPhoto);
    }

    /**
     * Returns the name of the group.
     *
     * @return The name of the group.
     */
    public String getName() {
        return name.GetName();
    }

    /**
     * Returns the photo of the group.
     *
     * @return The photo of the group.
     */
    public String getGroupPhoto() {
        return groupPhoto.getPhotoLink();
    }

    /**
     * Adds a currency to the group.
     *
     * @param currency The currency to be added.
     */
    public void addCurrency(Currency currency) {
        this.currencies.add(currency);
    }

    /**
     * Removes a currency from the group.
     *
     * @param currency The currency to be removed.
     */
    public void removeCurrency(Currency currency) {
        this.currencies.remove(currency);
    }

}