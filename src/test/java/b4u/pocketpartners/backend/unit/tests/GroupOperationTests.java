package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.groups.domain.model.aggregates.GroupOperation;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GroupOperationTests {

    private Group group;
    private Expense expense;
    private Payment payment;

    @BeforeEach
    void setUp() {
        group = new Group("Test Group", "Test Description", "photo.jpg");
        expense = new Expense();
        payment = new Payment();
    }

    @Test
    void shouldInitializeWithDefaultConstructor() {
        // Act
        GroupOperation groupOperation = new GroupOperation();

        // Assert
        assertNull(groupOperation.getGroup());
        assertNull(groupOperation.getExpense());
        assertNull(groupOperation.getPayment());
    }

    @Test
    void shouldInitializeWithParameterizedConstructor() {
        // Act
        GroupOperation groupOperation = new GroupOperation(group, expense, payment);

        // Assert
        assertEquals(group, groupOperation.getGroup());
        assertEquals(expense, groupOperation.getExpense());
        assertEquals(payment, groupOperation.getPayment());
    }

    @Test
    void shouldSetGroupCorrectly() {
        // Act
        GroupOperation groupOperation = new GroupOperation(group, null, null);

        // Assert
        assertEquals(group, groupOperation.getGroup());
        assertNull(groupOperation.getExpense());
        assertNull(groupOperation.getPayment());
    }

    @Test
    void shouldSetExpenseCorrectly() {
        // Act
        GroupOperation groupOperation = new GroupOperation(null, expense, null);

        // Assert
        assertEquals(expense, groupOperation.getExpense());
        assertNull(groupOperation.getGroup());
        assertNull(groupOperation.getPayment());
    }

    @Test
    void shouldSetPaymentCorrectly() {
        // Act
        GroupOperation groupOperation = new GroupOperation(null, null, payment);

        // Assert
        assertEquals(payment, groupOperation.getPayment());
        assertNull(groupOperation.getGroup());
        assertNull(groupOperation.getExpense());
    }
}
