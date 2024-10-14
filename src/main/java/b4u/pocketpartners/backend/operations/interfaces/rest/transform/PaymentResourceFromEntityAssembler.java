package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment payment) {
        return new PaymentResource(payment.getId(), payment.getDescription(), payment.getAmount(), payment.getStatus(), payment.getUserInformation().getId(), payment.getExpense().getId());
    }
}
