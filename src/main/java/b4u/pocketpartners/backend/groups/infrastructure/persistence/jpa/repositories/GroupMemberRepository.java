package b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories;

import b4u.pocketpartners.backend.groups.domain.model.entities.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    Optional<GroupMember> findByGroupIdAndUserInformationId(Long groupId, Long userInformationId);
    List<GroupMember> findAllByGroupId(Long groupId);
    List<GroupMember> findAllByUserInformationUserId(Long userInformationId);
}
