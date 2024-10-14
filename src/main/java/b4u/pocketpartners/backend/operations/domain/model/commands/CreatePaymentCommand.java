package b4u.pocketpartners.backend.operations.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePaymentCommand(String description, BigDecimal amount, Long userId, Long expenseId) {
}
