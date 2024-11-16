package b4u.pocketpartners.backend.unit.tests;


import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.PaymentStatus;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTests {

    private Payment payment;
    private UserInformation userInformation;
    private Expense expense;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        userInformation = Mockito.mock(UserInformation.class);
        expense = Mockito.mock(Expense.class);

        // Create Payment object
        payment = new Payment("Test Payment", BigDecimal.valueOf(100.00), userInformation, expense);
    }

    @Test
    void testCreatePayment() {
        assertNotNull(payment);
        assertEquals("Test Payment", payment.getDescription());
        assertEquals(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP), payment.getAmount().setScale(2, RoundingMode.HALF_UP));
        assertEquals(PaymentStatus.PENDING.name().toLowerCase(), payment.getStatus());
    }

    @Test
    void testUpdatePaymentInformation() {
        payment.UpdateInformation("Updated Payment", BigDecimal.valueOf(200.00));
        assertEquals("Updated Payment", payment.getDescription());
        assertEquals(0, payment.getAmount().compareTo(BigDecimal.valueOf(200.00)));
    }

    @Test
    void testCompletePayment() {
        payment.completePayment();
        assertEquals(PaymentStatus.COMPLETED.name().toLowerCase(), payment.getStatus());
    }

    @Test
    void testGetAmount() {
        assertEquals(0, payment.getAmount().compareTo(BigDecimal.valueOf(100.00)));
    }

    @Test
    void testGetDescription() {
        assertEquals("Test Payment", payment.getDescription());
    }

    @Test
    void testGetStatus() {
        assertEquals(PaymentStatus.PENDING.name().toLowerCase(), payment.getStatus());
    }
}