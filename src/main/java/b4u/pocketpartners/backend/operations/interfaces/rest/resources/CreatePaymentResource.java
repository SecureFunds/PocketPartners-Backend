package b4u.pocketpartners.backend.operations.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePaymentResource(@NotNull
                                    String description,
                                    @NotNull
                                    BigDecimal amount,
                                    @NotNull
                                    String receipt,
                                    @NotNull
                                    Long userId,
                                    @NotNull
                                    Long expenseId
) {
}
