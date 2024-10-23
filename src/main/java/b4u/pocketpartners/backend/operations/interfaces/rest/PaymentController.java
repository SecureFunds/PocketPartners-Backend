package b4u.pocketpartners.backend.operations.interfaces.rest;

import b4u.pocketpartners.backend.operations.domain.model.commands.CompletePaymentCommand;
import b4u.pocketpartners.backend.operations.domain.model.queries.*;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.PaymentStatus;
import b4u.pocketpartners.backend.operations.domain.services.PaymentCommandService;
import b4u.pocketpartners.backend.operations.domain.services.PaymentQueryService;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreatePaymentResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.PaymentResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.transform.CreatePaymentCommandFromResourceAssembler;
import b4u.pocketpartners.backend.operations.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import b4u.pocketpartners.backend.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Payments", description = "Payments Management Endpoint")
public class PaymentController {
    private final PaymentQueryService paymentQueryService;
    private final PaymentCommandService paymentCommandService;

    public PaymentController(PaymentQueryService paymentQueryService, PaymentCommandService paymentCommandService) {
        this.paymentQueryService = paymentQueryService;
        this.paymentCommandService = paymentCommandService;
    }

    @PostMapping
    public ResponseEntity<PaymentResource> createPayment(@RequestBody CreatePaymentResource resource) {
        var command = CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource);
        var paymentId = paymentCommandService.handle(command);
        System.out.println("Payment ID: " + paymentId);
        var getPaymentByUserIdAndExpenseId = new GetPaymentByUserInformationIdAndExpenseId(resource.userId(), resource.expenseId());
        var payment = paymentQueryService.handle(getPaymentByUserIdAndExpenseId);
        if(payment.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment.get());
        return new ResponseEntity<>(paymentResource, HttpStatus.CREATED);
    }

    @PostMapping("/{paymentId}/completed")
    public ResponseEntity<MessageResource> completePayment(@PathVariable Long paymentId) {
        var completePaymentCommand = new CompletePaymentCommand(paymentId);
        paymentCommandService.handle(completePaymentCommand);
        return ResponseEntity.ok(new MessageResource("Completed Payment with ID: " + paymentId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResource>> getAllPayments() {
        var getAllPaymentsQuery = new GetAllPaymentsQuery();
        var payments = paymentQueryService.handle(getAllPaymentsQuery);
        var paymentResources = payments.stream().map(PaymentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(paymentResources);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<PaymentResource>> getPaymentByUserId(@PathVariable Long userId) {
        var getAllPaymentsByUserIdQuery = new GetAllPaymentsByUserInformationIdQuery(userId);
        var payments = paymentQueryService.handle(getAllPaymentsByUserIdQuery);
        if (payments.isEmpty()){return ResponseEntity.notFound().build();}
        var paymentResources = payments.stream().map(PaymentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(paymentResources);
    }

    @GetMapping("/expenseId/{expenseId}")
    public ResponseEntity<List<PaymentResource>> getPaymentByExpenseId(@PathVariable Long expenseId) {
        var getAllPaymentsByExpenseIdQuery = new GetAllPaymentsByExpenseIdQuery(expenseId);
        var payments = paymentQueryService.handle(getAllPaymentsByExpenseIdQuery);
        if (payments.isEmpty()){return ResponseEntity.notFound().build();}
        var paymentResources = payments.stream().map(PaymentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(paymentResources);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResource> getPaymentById(@PathVariable Long paymentId) {
        var getPaymentByIdQuery = new GetPaymentByIdQuery(paymentId);
        var payment = paymentQueryService.handle(getPaymentByIdQuery);
        if (payment.isEmpty()) return ResponseEntity.badRequest().build();
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment.get());
        return ResponseEntity.ok(paymentResource);
    }

    @GetMapping("/userId/{userId}/status/{status}")
    public ResponseEntity<List<PaymentResource>> getPaymentByGroupIdAndUserIdAndStatus(@PathVariable Long userId, @PathVariable PaymentStatus status) {
        var getAllPaymentsByUserIdAndStatusQuery = new GetAllPaymentsByUserIdAndStatusQuery(userId, status);
        var payments = paymentQueryService.handle(getAllPaymentsByUserIdAndStatusQuery);
        if (payments.isEmpty()){return ResponseEntity.notFound().build();}
        var paymentResources = payments.stream().map(PaymentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(paymentResources);
    }
}
