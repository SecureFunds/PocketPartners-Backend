package b4u.pocketpartners.backend.groups.domain.model.aggregates;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateGroupCommand;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.InvitationToken;
import b4u.pocketpartners.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

/**
 * The Group class represents a group entity in the system.
 * It extends the AuditableAbstractAggregateRoot class, which provides audit fields for the entity.
 */
@Getter
@Entity
@Table(name = "pocket_groups")
public class Group extends AuditableAbstractAggregateRoot<Group> {


    private String name;
    private String groupPhoto;
    private String description;
    private String invitationToken;


    public Group() {
        this.name = Strings.EMPTY;
        this.description = Strings.EMPTY;
        this.groupPhoto = Strings.EMPTY;
        this.invitationToken = Strings.EMPTY;

    }

    public Group(String name, String description, String photo) {
        this();
        this.name = name;
        this.description = description;
        this.groupPhoto = photo;
    }

    public Group(CreateGroupCommand command) {
        this();
        this.name = command.name();
        this.description = command.description();
        this.groupPhoto = command.groupPhoto();

    }

    public Group updateInformation(String name, String description, String photo) {
        this.name = name;
        this.description = description;
        this.groupPhoto = photo;
        return this;
    }

    public void generateInvitationToken() {
        this.invitationToken = new InvitationToken().getToken();
    }

    public boolean hasValidInvitationToken(String token) {
        return token.equals(this.invitationToken);
    }

}
