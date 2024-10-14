package b4u.pocketpartners.backend.operations.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseResource(@NotNull
                                    String name,
                                    @NotNull
                                    BigDecimal amount,
                                    @NotNull
                                    Long userId,
                                    @NotNull
                                    Long groupId,
                                    @NotNull
                                    LocalDate dueDate) {
}
