package b4u.pocketpartners.backend.groups.interfaces.rest;

import b4u.pocketpartners.backend.groups.domain.model.queries.GetReceiptByIdQuery;
import b4u.pocketpartners.backend.groups.domain.services.ReceiptCommandService;
import b4u.pocketpartners.backend.groups.domain.services.ReceiptQueryService;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.CreateReceiptResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.resources.ReceiptResource;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.CreateReceiptCommandFromResourceAssembler;
import b4u.pocketpartners.backend.groups.interfaces.rest.transform.ReceiptResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/receipts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Receipts", description = "Receipts Management Endpoints")
public class ReceiptController {
    private final ReceiptCommandService receiptCommandService;
    private final ReceiptQueryService receiptQueryService;

    public ReceiptController(ReceiptCommandService receiptCommandService, ReceiptQueryService receiptQueryService) {
        this.receiptCommandService = receiptCommandService;
        this.receiptQueryService = receiptQueryService;
    }

    @PostMapping
    public ResponseEntity<ReceiptResource> createReceipt(@RequestBody CreateReceiptResource resource){
        var createReceiptCommand = CreateReceiptCommandFromResourceAssembler.toCommandFromResource(resource);
        var receiptId = receiptCommandService.handle(createReceiptCommand);
        if (receiptId == 0L) return ResponseEntity.badRequest().build();
        var getReceiptByIdQuery = new GetReceiptByIdQuery(receiptId);
        var receipt = receiptQueryService.handle(getReceiptByIdQuery);
        if (receipt.isEmpty()) return ResponseEntity.badRequest().build();
        var receiptResource = ReceiptResourceFromEntityAssembler.fromEntity(receipt.get());
        return new ResponseEntity<>(receiptResource, HttpStatus.CREATED);
    }
}
