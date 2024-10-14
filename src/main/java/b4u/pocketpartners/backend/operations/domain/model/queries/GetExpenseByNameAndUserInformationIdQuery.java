package b4u.pocketpartners.backend.operations.domain.model.queries;

import b4u.pocketpartners.backend.operations.domain.model.valueobjects.ExpenseName;

public record GetExpenseByNameAndUserInformationIdQuery(ExpenseName expenseName, Long userInformationId) {
}
