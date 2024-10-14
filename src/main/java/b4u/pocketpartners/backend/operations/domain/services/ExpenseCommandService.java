package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.domain.model.commands.CreateExpenseCommand;
import b4u.pocketpartners.backend.operations.domain.model.commands.DeleteExpenseCommand;
import b4u.pocketpartners.backend.operations.domain.model.commands.UpdateExpenseCommand;

import java.util.Optional;

public interface ExpenseCommandService {
    Optional<Expense> handle(CreateExpenseCommand command);
    Optional<Expense> handle(UpdateExpenseCommand command);
    void handle(DeleteExpenseCommand command);
}
