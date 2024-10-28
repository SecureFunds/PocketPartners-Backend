package b4u.pocketpartners.backend.unit.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.users.domain.model.aggregates.User;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class ExpenseTests {

    @Mock
    private Group mockGroup;

    @Mock
    private UserInformation mockUserInformation;

    @Mock
    private User mockUser;

    private Expense expense;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar los mocks
        when(mockUserInformation.getUser()).thenReturn(mockUser);
        when(mockUser.getUsername()).thenReturn("username");

        // Crear una instancia de Expense usando valores válidos
        expense = new Expense("Rent", BigDecimal.valueOf(1000.00), mockUserInformation, mockGroup, LocalDate.now().plusDays(30));
    }

    @Test
    void expenseInitializationWithAttributes() {
        // Verificar que los atributos se asignan correctamente al usar el constructor
        assertEquals("Rent", expense.getName());
        assertEquals(0, expense.getAmount().compareTo(BigDecimal.valueOf(1000.00)));
        assertEquals(LocalDate.now().plusDays(30), expense.getDueDate());
        assertEquals(mockUserInformation, expense.getUserInformation());
        assertEquals(mockGroup, expense.getGroup());
    }



    @Test
    void updateExpenseName() {
        // Act
        expense.UpdateExpenseName("Utilities");

        // Assert
        assertEquals("Utilities", expense.getName());
    }

    @Test
    void updateAmount() {
        // Act
        expense.UpdateAmount(BigDecimal.valueOf(1200.00));

        // Assert
        assertEquals(new BigDecimal("1200.00"), expense.getAmount());
    }

    @Test
    void updateInformation() {
        // Act
        expense.UpdateInformation("Maintenance", BigDecimal.valueOf(1500.00), LocalDate.now().plusDays(30));

        // Assert
        assertEquals("Maintenance", expense.getName());
        assertEquals(0, expense.getAmount().compareTo(BigDecimal.valueOf(1500.00)));
        assertEquals(LocalDate.now().plusDays(30), expense.getDueDate());
    }
}