package b4u.pocketpartners.backend.groups.domain.services;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Receipt;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllReceiptsByGroupIdQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllReceiptsByUserId;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetAllReceiptsQuery;
import b4u.pocketpartners.backend.groups.domain.model.queries.GetReceiptByIdQuery;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public interface ReceiptQueryService {
    List<Receipt> handle(GetAllReceiptsQuery query);
    Optional<Receipt> handle(GetReceiptByIdQuery query);
    Optional<Receipt> handle(GetAllReceiptsByGroupIdQuery query);
    Optional<Receipt> handle(GetAllReceiptsByUserId query);
}
