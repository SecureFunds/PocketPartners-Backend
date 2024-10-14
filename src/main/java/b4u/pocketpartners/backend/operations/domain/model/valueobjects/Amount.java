package b4u.pocketpartners.backend.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
public record Amount(BigDecimal amount) {

    public Amount() {
        this(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
    }

    public Amount(BigDecimal amount) {
        this.amount = validateAndSetAmount(amount);
    }

    public static BigDecimal validateAndSetAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if(amount.scale() > 2) {
            throw new IllegalArgumentException("Amount cannot have at most 2 decimal places");
        }
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
