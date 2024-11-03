package b4u.pocketpartners.backend.groups.domain.model.entities;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupMemberId;
import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupRole;
import b4u.pocketpartners.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * The GroupMember class represents a member of a group in the system.
 * It is an entity class that is linked to both the Group and User aggregate roots.
 */
@Getter
@Entity
@IdClass(GroupMemberId.class)
@EntityListeners(AuditingEntityListener.class)
public class GroupMember {

    /**
     * The group that the user is a member of.
     * It is a many-to-one relationship, as a group can have many members.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    /**
     * The user who is a member of the group.
     * It is a many-to-one relationship, as a user can be a member of many groups.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInformation userInformation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupRole role;

    /**
     * The date when the user joined the group.
     * It is automatically updated whenever the entity is persisted.
     */
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
    @Column(nullable =  false)
    private Date joinedAt;

    /**
     * Constructs a new GroupMember with the provided group and user.
     *
     * @param group The group that the user is joining.
     * @param userInformation The user who is joining the group.
     */
    public GroupMember(Group group, UserInformation userInformation, GroupRole role) {
        this.group = group;
        this.userInformation = userInformation;
        this.role = role;
        this.joinedAt = new Date();
    }

    public GroupMember() {
    }

    public boolean isAdmin() {
        return this.role == GroupRole.ADMIN;
    }


}
