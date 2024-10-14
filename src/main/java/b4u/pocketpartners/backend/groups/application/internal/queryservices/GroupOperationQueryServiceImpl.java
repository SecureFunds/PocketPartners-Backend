package b4u.pocketpartners.backend.groups.application.internal.queryservices;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.GroupOperation;
import b4u.pocketpartners.backend.groups.domain.model.queries.*;
import b4u.pocketpartners.backend.groups.domain.services.GroupOperationQueryService;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.GroupOperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupOperationQueryServiceImpl implements GroupOperationQueryService {
    private final GroupOperationRepository groupOperationRepository;

    public GroupOperationQueryServiceImpl(GroupOperationRepository groupOperationRepository) {
        this.groupOperationRepository = groupOperationRepository;
    }

    @Override
    public List<GroupOperation> handle(GetAllGroupOperationsQuery query) {return groupOperationRepository.findAll();}

    @Override
    public Optional<GroupOperation> handle(GetGroupOperationByIdQuery query) {
        return groupOperationRepository.findById(query.groupOperationId());
    }

    @Override
    public List<GroupOperation> handle(GetAllGroupOperationsByGroupIdQuery query) {
        return groupOperationRepository.findAllByGroupId(query.groupId());
    }

    @Override
    public Optional<GroupOperation> handle(GetGroupOperationByGroupIdAndExpenseIdAndPaymentId query){
        return groupOperationRepository.findByGroupIdAndExpenseIdAndPaymentId(query.groupId(), query.expenseId(), query.paymentId());
    }

    @Override
    public List<GroupOperation> handle(GetAllGroupOperationByExpenseIdQuery query) {
        return groupOperationRepository.findByExpenseId(query.expenseId());
    }




}
