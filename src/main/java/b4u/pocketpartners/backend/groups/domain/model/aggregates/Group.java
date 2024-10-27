package b4u.pocketpartners.backend.groups.domain.model.aggregates;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.domain.model.entities.Currency;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupName;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupPhoto;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.InvitationToken;
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

    @Embedded
    private GroupName name;

    @Embedded
    private GroupPhoto groupPhoto;

    @Embedded
    private InvitationToken invitationToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "group_currencies",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "currency_id")
    )
    private Set<Currency> currencies;

    /**
     * Constructs a new Group with the provided name and photo, and generates a unique invitation token.
     *
     * @param name The name of the group.
     * @param groupPhoto The photo of the group.
     */
    public Group(GroupName name, GroupPhoto groupPhoto) {
        this.name = name;
        this.groupPhoto = groupPhoto;
        this.invitationToken = new InvitationToken(); // Genera un token único al crear el grupo
    }

    /**
     * Constructs a new Group from a CreateGroupCommand and generates a unique invitation token.
     *
     * @param command The command containing the details of the group to be created.
     */
    public Group(CreateGroupCommand command) {
        this.name = new GroupName(command.name());
        this.groupPhoto = new GroupPhoto(command.groupPhoto());
        this.currencies = command.currency().stream()
                .map(Currency::new)
                .collect(Collectors.toSet());
        this.invitationToken = new InvitationToken(); // Genera un token único al crear el grupo
    }

    /**
     * Default constructor. Constructs a new Group with a default name, photo, and a unique invitation token.
     */
    public Group() {
        this.name = new GroupName();
        this.groupPhoto = new GroupPhoto();
        this.currencies = Set.of(Currency.getDefaultRole());
        this.invitationToken = new InvitationToken(); // Genera un token único al crear el grupo
    }

    public Group(String name, String s, Currency currency) {
        this.name = new GroupName(name);
        this.groupPhoto = new GroupPhoto(s);
        this.currencies = Set.of(currency);
        this.invitationToken = new InvitationToken(); // Genera un token único al crear el grupo
    }

    // Método para regenerar el token de invitación
    public void regenerateInvitationToken() {
        this.invitationToken = new InvitationToken(); // Genera un nuevo token único
    }

    // Métodos de cambio y acceso a otros atributos
    public void changeName(GroupName name) {
        this.name = name;
    }

    public void changeGroupPhoto(GroupPhoto groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public void changeName(String name) {
        this.name = new GroupName(name);
    }

    public void changeGroupPhoto(String groupPhoto) {
        this.groupPhoto = new GroupPhoto(groupPhoto);
    }

    public String getName() {
        return name.GetName();
    }

    public String getGroupPhoto() {
        return groupPhoto.getPhotoLink();
    }

    public void addCurrency(Currency currency) {
        this.currencies.add(currency);
    }

    public void removeCurrency(Currency currency) {
        this.currencies.remove(currency);
    }

    public String getInvitationToken() {
        return invitationToken.token();
    }
}
