package b4u.pocketpartners.backend.operations.domain.model.queries;

import java.time.LocalDate;

/**
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record GetAllExpensesByDueDate(LocalDate dueDate) {
}
