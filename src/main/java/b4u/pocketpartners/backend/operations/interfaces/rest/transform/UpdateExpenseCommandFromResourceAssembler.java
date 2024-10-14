package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.commands.UpdateExpenseCommand;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.UpdateExpenseResource;

public class UpdateExpenseCommandFromResourceAssembler {
    public static UpdateExpenseCommand toCommandFromResource(Long expenseId, UpdateExpenseResource resource) {
        return new UpdateExpenseCommand(expenseId, resource.name(), resource.amount(), resource.dueDate());
    }
}
