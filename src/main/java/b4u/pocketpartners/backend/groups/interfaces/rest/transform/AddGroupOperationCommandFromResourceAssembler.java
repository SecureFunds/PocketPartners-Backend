package b4u.pocketpartners.backend.groups.interfaces.rest.transform;

import b4u.pocketpartners.backend.groups.domain.model.commands.AddGroupOperationCommand;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.AddGroupOperationResource;

public class AddGroupOperationCommandFromResourceAssembler {
    public static AddGroupOperationCommand toCommandFromResource(AddGroupOperationResource resource) {
        return new AddGroupOperationCommand(resource.groupId(), resource.expenseId(), resource.paymentId());
    }
}
