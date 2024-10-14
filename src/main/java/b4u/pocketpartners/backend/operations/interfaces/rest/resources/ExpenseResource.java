package b4u.pocketpartners.backend.operations.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResource(Long id,
                              String name,
                              BigDecimal amount,
                              Long userId,
                              Long groupId,
                              LocalDate dueDate,
                              java.util.Date createdAt,
                              java.util.Date updatedAt) {
}
