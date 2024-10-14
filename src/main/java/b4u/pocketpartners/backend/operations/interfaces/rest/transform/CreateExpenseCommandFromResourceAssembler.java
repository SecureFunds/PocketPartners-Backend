package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.commands.CreateExpenseCommand;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreateExpenseResource;

public class CreateExpenseCommandFromResourceAssembler {
    public static CreateExpenseCommand toCommandFromResource(CreateExpenseResource resource) {
        return new CreateExpenseCommand(resource.name(), resource.amount(), resource.userId(), resource.groupId(), resource.dueDate());
    }
}
