package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.commands.CreateReceiptCommand;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public interface ReceiptCommandService {
    Long handle(CreateReceiptCommand command);
    //Optional<Receipt> handle(UpdateReceiptCommand command);
}
