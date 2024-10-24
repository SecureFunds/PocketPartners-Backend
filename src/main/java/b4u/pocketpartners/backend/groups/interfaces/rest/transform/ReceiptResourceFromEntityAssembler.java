package b4u.pocketpartners.backend.groups.interfaces.rest.transform;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Receipt;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.ReceiptResource;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public class ReceiptResourceFromEntityAssembler {
    public static ReceiptResource fromEntity(Receipt receipt) {
        return new ReceiptResource(
                receipt.getName(),
                receipt.getDescription(),
                receipt.getPhoto(),
                receipt.getGroupId(),
                receipt.getUserId()
        );
    }
}
