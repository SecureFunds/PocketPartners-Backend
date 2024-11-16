package b4u.pocketpartners.backend.unit.tests;


import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTests {

    private Expense expense;
    private UserInformation userInformation;
    private Group group;

    @BeforeEach
    void setUp() {
        userInformation = Mockito.mock(UserInformation.class);
        group = Mockito.mock(Group.class);
        expense = new Expense("Test Expense", BigDecimal.valueOf(100.00), userInformation, group, LocalDate.now().plusDays(1));
    }

    @Test
    void testCreateExpense() {
        assertNotNull(expense);
        assertEquals("Test Expense", expense.getName());
        assertEquals(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP), expense.getAmount().setScale(2, RoundingMode.HALF_UP));
        assertEquals(LocalDate.now().plusDays(1), expense.getDueDate());
    }

    @Test
    void testCreateExpenseWithNullName() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Expense(null, BigDecimal.valueOf(100.00), userInformation, group, LocalDate.now().plusDays(1));
        });
        assertEquals("Expense name cannot be null or blank", thrown.getMessage());
    }

    @Test
    void testUpdateExpenseName() {
        expense.UpdateExpenseName("Updated Expense");
        assertEquals("Updated Expense", expense.getName());
    }

    @Test
    void testUpdateAmount() {
        expense.UpdateAmount(BigDecimal.valueOf(150.00));
        assertEquals(0, expense.getAmount().compareTo(BigDecimal.valueOf(150.00)));
    }

    @Test
    void testUpdateInformation() {
        expense.UpdateInformation("Updated Expense", BigDecimal.valueOf(200.00), LocalDate.now().plusDays(2));
        assertEquals("Updated Expense", expense.getName());
        assertEquals(0, expense.getAmount().compareTo(BigDecimal.valueOf(200.00)));
        assertEquals(LocalDate.now().plusDays(2), expense.getDueDate());
    }

    @Test
    void testUpdateInformationWithInvalidName() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            expense.UpdateInformation("", BigDecimal.valueOf(150.00), LocalDate.now().plusDays(1));
        });
        assertEquals("Expense name cannot be null or blank", thrown.getMessage());
    }


    @Test
    void testGetters() {
        assertEquals("Test Expense", expense.getName());
        assertEquals(0, expense.getAmount().compareTo(BigDecimal.valueOf(100.00)));
        assertEquals(LocalDate.now().plusDays(1), expense.getDueDate());
    }
}