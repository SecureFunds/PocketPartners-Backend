package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.commands.CompletePaymentCommand;
import b4u.pocketpartners.backend.operations.domain.model.commands.CreatePaymentCommand;

public interface PaymentCommandService {
    Long handle(CreatePaymentCommand command);
    Long handle(CompletePaymentCommand command);
}
