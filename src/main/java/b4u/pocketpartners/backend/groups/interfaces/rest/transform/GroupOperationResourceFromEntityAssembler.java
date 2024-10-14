package b4u.pocketpartners.backend.groups.interfaces.rest.transform;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.GroupOperation;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.GroupOperationResource;

public class GroupOperationResourceFromEntityAssembler {
    public static GroupOperationResource toResourceFromEntity(GroupOperation groupOperation) {
        return new GroupOperationResource(groupOperation.getId(), groupOperation.getGroup().getId(), groupOperation.getExpense().getId(), groupOperation.getPayment().getId());
    }
}
