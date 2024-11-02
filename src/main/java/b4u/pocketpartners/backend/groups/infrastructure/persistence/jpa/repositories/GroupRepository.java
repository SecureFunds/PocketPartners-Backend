package b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndIdIsNot(String name, Long id);
}
