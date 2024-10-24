package b4u.pocketpartners.backend.groups.interfaces.rest.transform;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateReceiptCommand;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.CreateReceiptResource;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public class CreateReceiptCommandFromResourceAssembler {
    public static CreateReceiptCommand toCommandFromResource(CreateReceiptResource resource) {
        return new CreateReceiptCommand(
                resource.name(),
                resource.description(),
                resource.photo(),
                resource.groupId(),
                resource.userId()
        );
    }
}
