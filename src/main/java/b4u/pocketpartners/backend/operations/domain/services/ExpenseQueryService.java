package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ExpenseQueryService {
    List<Expense> handle(GetAllExpensesQuery query);
    Optional<Expense> handle(GetExpenseByIdQuery query);
    List<Expense> handle(GetAllExpensesByUserInformationIdQuery query);
    Optional<Expense> handle(GetExpenseByNameAndUserInformationIdQuery query);
    List<Expense> handle(GetAllExpensesByGroupIdQuery query);
    List<Expense> handle(GetAllExpensesByDueDate query);
}
