package b4u.pocketpartners.backend.groups.application.internal.queryservices;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Receipt;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllReceiptsByGroupIdQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllReceiptsByUserId;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllReceiptsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetReceiptByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.ReceiptQueryService;
import b4u.pocketpartners.backend.groups.infrastructure.persistence.jpa.repositories.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@Service
public class ReceiptQueryServiceImpl implements ReceiptQueryService {

    private final ReceiptRepository receiptRepository;

    public ReceiptQueryServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public List<Receipt> handle(GetAllReceiptsQuery query) {
        return receiptRepository.findAll();
    }

    @Override
    public Optional<Receipt> handle(GetReceiptByIdQuery query) {
        return receiptRepository.findById(query.receiptId());
    }

    @Override
    public Optional<Receipt> handle(GetAllReceiptsByGroupIdQuery query) {
        return receiptRepository.findById(query.groupId());
    }

    @Override
    public Optional<Receipt> handle(GetAllReceiptsByUserId query) {
        return receiptRepository.findById(query.userId());
    }
}
