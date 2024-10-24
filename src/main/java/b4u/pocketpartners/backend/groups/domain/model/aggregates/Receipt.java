package b4u.pocketpartners.backend.groups.domain.model.aggregates;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateReceiptCommand;
import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Description;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Name;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Photo;
import b4u.pocketpartners.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@Getter
@Entity
public class Receipt extends AuditableAbstractAggregateRoot<Receipt> {
    @Setter
    @Embedded
    private Name name;

    @Setter
    @Embedded
    private Description description;

    @Setter
    @Embedded
    private Photo photo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "group_id", referencedColumnName = "group_id"),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    })
    private GroupMember groupMember;

    public Receipt(String name, String description, String photo, GroupMember groupMember){
        this.name = new Name(name);
        this.description = new Description(description);
        this.photo = new Photo(photo);
        this.groupMember = groupMember;
    }

    public Receipt(Name name, Description description, Photo photo, GroupMember groupMember){
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.groupMember = groupMember;
    }

    public Receipt(CreateReceiptCommand command, GroupMember groupMember){
        if (command == null || groupMember == null) {
            throw new IllegalArgumentException("Command and group member cannot be null");
        }
        this.name = new Name(command.name());
        this.description = new Description(command.description());
        this.photo = new Photo(command.photo());
        this.groupMember = groupMember;
    }


    public Receipt(){}

    public String getName(){
        return name.name();
    }

    public String getDescription(){
        return description.description();
    }

    public String getPhoto(){
        return photo.photo();
    }

    public Long getGroupId() {
        return groupMember.getGroupId();
    }

    public Long getUserId() {
        return groupMember.getUserId();
    }
}
