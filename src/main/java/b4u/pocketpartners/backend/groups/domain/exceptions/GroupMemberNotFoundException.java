package b4u.pocketpartners.backend.groups.domain.exceptions;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public class GroupMemberNotFoundException extends RuntimeException {
    public GroupMemberNotFoundException(Long aLong, Long aLong1) {
        super("Group member with group id " + aLong + " and user id " + aLong1 + " not found" );
    }
}
