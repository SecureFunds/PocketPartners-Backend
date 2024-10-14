package b4u.pocketpartners.backend.groups.application.internal.commandservices;

import b4u.pocketpartners.backend.groups.domain.exceptions.GroupNotFoundException;
import b4u.pocketpartners.backend.groups.domain.exceptions.PaymentNotFoundException;
import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.aggregates.GroupOperation;
import b4u.pocketpartners.backend.groups.domain.model.commands.AddGroupOperationCommand;
import b4u.pocketpartners.backend.groups.domain.model.commands.DeleteGroupOperationCommand;
import b4u.pocketpartners.backend.groups.domain.services.GroupOperationCommandService;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupOperationRepository;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupRepository;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.ExpenseRepository;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupOperationCommandServiceImpl implements GroupOperationCommandService {
    private final GroupOperationRepository groupOperationRepository;
    private final GroupRepository groupRepository;
    private final ExpenseRepository expenseRepository;
    private final PaymentRepository paymentRepository;

    public GroupOperationCommandServiceImpl(GroupOperationRepository groupOperationRepository, GroupRepository groupRepository, ExpenseRepository expenseRepository, PaymentRepository paymentRepository) {
        this.groupOperationRepository = groupOperationRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
        this.paymentRepository = paymentRepository;
    }

    public Long handle(AddGroupOperationCommand command) {
        expenseRepository.findById(command.expenseId()).map(expense -> {
            Group group = groupRepository.findById(command.groupId()).orElseThrow(() -> new GroupNotFoundException(command.groupId()));
            Payment payment = paymentRepository.findById(command.paymentId()).orElseThrow(() -> new PaymentNotFoundException(command.paymentId()));
            GroupOperation groupOperation = new GroupOperation(group, expense, payment);
            groupOperation = groupOperationRepository.save(groupOperation);
            return groupOperation.getId();
        }).orElseThrow(() -> new RuntimeException("Group not found"));
        return 0L;
    }


    public void handle(DeleteGroupOperationCommand command) {
        if(!groupOperationRepository.existsById(command.groupOperationId())){
            throw new IllegalArgumentException("Group does not exist");
        }
        try{
            groupOperationRepository.deleteById(command.groupOperationId());
        } catch(Exception e){
            throw new IllegalArgumentException("Error while deleting group operation: " + e.getMessage());
        }
    }
}
