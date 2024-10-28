package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.PaymentStatus;
import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class PaymentTests {

    @Mock
    private UserInformation mockUserInformation;

    @Mock
    private Expense mockExpense;

    @Mock
    private User mockUser;

    private Payment payment;  // Instanciamos manualmente

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración de mocks
        when(mockUserInformation.getUser()).thenReturn(mockUser);
        when(mockUser.getUsername()).thenReturn("username");

        // Crear una instancia de Payment usando mocks y valores válidos
        payment = new Payment("Initial Payment", BigDecimal.valueOf(500.00), mockUserInformation, mockExpense);
    }


    @Test
    void paymentInitializationWithAttributes() {
        // Verificar que los atributos se asignan correctamente al usar el constructor
        assertEquals("Initial Payment", payment.getDescription());
        assertEquals(BigDecimal.valueOf(500.00).setScale(2), payment.getAmount().setScale(2));
        assertEquals(PaymentStatus.PENDING.name().toLowerCase(), payment.getStatus());
        assertEquals(mockUserInformation, payment.getUserInformation());
        assertEquals(mockExpense, payment.getExpense());
    }


    @Test
    void updatePaymentInformation() {
        // Act
        payment.UpdateInformation("Updated Payment", BigDecimal.valueOf(750.00));

        // Assert
        assertEquals("Updated Payment", payment.getDescription());
        assertEquals(BigDecimal.valueOf(750.00).setScale(2), payment.getAmount().setScale(2));
    }

    @Test
    void completePayment() {
        // Act
        payment.completePayment();

        // Assert
        assertEquals(PaymentStatus.COMPLETED.name().toLowerCase(), payment.getStatus());
    }
}