package b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllByGroupId(Long groupId);
    List<Receipt> findAllByUserId(Long userId);
}
