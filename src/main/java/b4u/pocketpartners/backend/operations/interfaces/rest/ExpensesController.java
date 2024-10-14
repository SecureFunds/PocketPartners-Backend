package b4u.pocketpartners.backend.operations.interfaces.rest;

import b4u.pocketpartners.backend.operations.domain.model.commands.DeleteExpenseCommand;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllExpensesByGroupIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllExpensesByUserInformationIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllExpensesQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetExpenseByIdQuery;
import b4u.pocketpartners.backend.operations.domain.services.ExpenseCommandService;
import b4u.pocketpartners.backend.operations.domain.services.ExpenseQueryService;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreateExpenseResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.ExpenseResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.UpdateExpenseResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.transform.CreateExpenseCommandFromResourceAssembler;
import b4u.pocketpartners.backend.operations.interfaces.rest.transform.ExpenseResourceFromEntityAssembler;
import b4u.pocketpartners.backend.operations.interfaces.rest.transform.UpdateExpenseCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Expenses", description = "Expenses Management Endpoints")
public class ExpensesController {

    private final ExpenseQueryService expenseQueryService;
    private final ExpenseCommandService expenseCommandService;

    public ExpensesController(ExpenseQueryService expenseQueryService, ExpenseCommandService expenseCommandService) {
        this.expenseQueryService = expenseQueryService;
        this.expenseCommandService = expenseCommandService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResource> createExpense(@RequestBody CreateExpenseResource resource) {
        var createExpenseCommand = CreateExpenseCommandFromResourceAssembler.toCommandFromResource(resource);
        var expenseId = expenseCommandService.handle(createExpenseCommand);
        //var getExpenseByNameAndUserId = new GetExpenseByNameAndUserInformationIdQuery(new ExpenseName(resource.name()), resource.userId());
        //var expense = expenseQueryService.handle(getExpenseByNameAndUserId);
        //if (expense.isEmpty()) return ResponseEntity.badRequest().build();
        if (expenseId.isEmpty()) return ResponseEntity.badRequest().build();
        var expenseResource = ExpenseResourceFromEntityAssembler.toResourceFromEntity(expenseId.get());
        return new ResponseEntity<>(expenseResource, HttpStatus.CREATED);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResource> getExpenseById(@PathVariable Long expenseId) {
        var getExpenseByIdQuery = new GetExpenseByIdQuery(expenseId);
        var expense = expenseQueryService.handle(getExpenseByIdQuery);
        if (expense.isEmpty()) return ResponseEntity.badRequest().build();
        var expenseResource = ExpenseResourceFromEntityAssembler.toResourceFromEntity(expense.get());
        return ResponseEntity.ok(expenseResource);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<ExpenseResource>> getExpensesByUserId(@PathVariable Long userId) {
        var getAllExpensesByUserIdQuery = new GetAllExpensesByUserInformationIdQuery(userId);
        var expenses = expenseQueryService.handle(getAllExpensesByUserIdQuery);
        if (expenses.isEmpty()) {return ResponseEntity.badRequest().build();}
        var expenseResources = expenses.stream().map(ExpenseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(expenseResources);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResource>> getAllExpenses() {
        var getAllExpensesQuery = new GetAllExpensesQuery();
        var expenses = expenseQueryService.handle(getAllExpensesQuery);
        var expensesResources = expenses.stream().map(ExpenseResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(expensesResources);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResource> updateExpense(@PathVariable Long expenseId, @RequestBody UpdateExpenseResource updateExpenseResource) {
        var updateExpenseCommand = UpdateExpenseCommandFromResourceAssembler.toCommandFromResource(expenseId, updateExpenseResource);
        var updatedExpense = expenseCommandService.handle(updateExpenseCommand);
        if(updatedExpense.isEmpty()) return ResponseEntity.badRequest().build();
        var expenseResource = ExpenseResourceFromEntityAssembler.toResourceFromEntity(updatedExpense.get());
        return ResponseEntity.ok(expenseResource);
    }

    @GetMapping("/groupId/{groupId}")
    public ResponseEntity<List<ExpenseResource>> getExpensesByGroupId(@PathVariable Long groupId) {
        var getAllExpensesByGroupIdQuery = new GetAllExpensesByGroupIdQuery(groupId);
        var expenses = expenseQueryService.handle(getAllExpensesByGroupIdQuery);
        if (expenses.isEmpty()) {return ResponseEntity.badRequest().build();}
        var expenseResources = expenses.stream().map(ExpenseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(expenseResources);
    }

    @DeleteMapping("expenseId/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        var deleteExpenseCommand = new DeleteExpenseCommand(expenseId);
        expenseCommandService.handle(deleteExpenseCommand);
        return ResponseEntity.noContent().build();
    }


}
