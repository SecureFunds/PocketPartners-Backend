package b4u.pocketpartners.backend.operations.domain.model.queries;

import b4u.pocketpartners.backend.operations.domain.model.valueobjects.PaymentStatus;

public record GetAllPaymentsByUserIdAndStatusQuery(Long userInformationId, PaymentStatus status) {
}
