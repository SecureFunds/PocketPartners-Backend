package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.ExpenseResource;

public class ExpenseResourceFromEntityAssembler {
    public static ExpenseResource toResourceFromEntity(Expense expense){
        return new ExpenseResource(expense.getId(), expense.getName(), expense.getAmount(), expense.getUserInformation().getId(), expense.getGroup().getId(), expense.getDueDate(), expense.getCreatedAt(), expense.getUpdatedAt());
    }
}
