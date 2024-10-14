package b4u.pocketpartners.backend.operations.interfaces.acl;

import b4u.pocketpartners.backend.operations.domain.services.ExpenseCommandService;
import b4u.pocketpartners.backend.operations.domain.services.ExpenseQueryService;
import org.springframework.stereotype.Service;

@Service
public class ExpensesContextFacade {

    private final ExpenseQueryService expenseQueryService;
    private final ExpenseCommandService expenseCommandService;

    public ExpensesContextFacade(ExpenseQueryService expenseQueryService, ExpenseCommandService expenseCommandService) {
        this.expenseQueryService = expenseQueryService;
        this.expenseCommandService = expenseCommandService;
    }

    //public Long createExpense(String name, BigDecimal amount, Long userId) {
    //    var createExpenseCommand = new CreateExpenseCommand(name, amount, userId);
    //    var expense = expenseCommandService.handle(createExpenseCommand);
    //    if(expense == null) return 0L;
    //    return expense.toString().getId();
    //}
}
