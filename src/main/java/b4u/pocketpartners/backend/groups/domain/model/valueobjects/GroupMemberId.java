package b4u.pocketpartners.backend.groups.domain.model.valueobjects;

import java.io.Serializable;
import java.util.Objects;

public class GroupMemberId implements Serializable {
    private Long group;
    private Long userInformation;

    // Default constructor
    public GroupMemberId() {}

    // Getters and setters
    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public Long getUser() {
        return userInformation;
    }

    public void setUser(Long userInformation) {
        this.userInformation = userInformation;
    }

    // hashCode and equals implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(group, that.group) && Objects.equals(userInformation, that.userInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, userInformation);
    }
}