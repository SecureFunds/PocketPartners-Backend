package b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories;

import b4u.pocketpartners.backend.groups.domain.model.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupCurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCode(String code);
}
