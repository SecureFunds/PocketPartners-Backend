package b4u.pocketpartners.backend.integration.tests;

import b4u.pocketpartners.backend.groups.domain.model.aggregates.Group;
import b4u.pocketpartners.backend.operations.domain.model.commands.CreateExpenseCommand;
import b4u.pocketpartners.backend.operations.domain.model.commands.DeleteExpenseCommand;
import b4u.pocketpartners.backend.operations.domain.model.commands.UpdateExpenseCommand;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllExpensesByGroupIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllExpensesByUserInformationIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetExpenseByIdQuery;
import b4u.pocketpartners.backend.operations.interfaces.rest.ExpensesController;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreateExpenseResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.ExpenseResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.UpdateExpenseResource;
import b4u.pocketpartners.backend.operations.domain.services.ExpenseCommandService;
import b4u.pocketpartners.backend.operations.domain.services.ExpenseQueryService;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ExpenseControllerIT {

    @InjectMocks
    private ExpensesController expensesController;

    @Mock
    private ExpenseQueryService expenseQueryService;

    @Mock
    private ExpenseCommandService expenseCommandService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        // Inicialización de MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(expensesController).build();
    }
    @Test
    void testCreateExpense() throws Exception {
        // Arrange: Crear el recurso de gasto y simular el comportamiento del servicio
        CreateExpenseResource createExpenseResource = new CreateExpenseResource(
                "Test Expense",
                BigDecimal.valueOf(100.00),
                1L,  // userId
                1L,  // groupId
                LocalDate.now().plusDays(1)
        );

        // Simulamos que el servicio retorna un Expense (no un ExpenseResource)
        Expense expense = new Expense(
                "Test Expense",
                BigDecimal.valueOf(100.00),
                new UserInformation(),  // Simulamos el usuario
                new Group(),  // Simulamos el grupo
                LocalDate.now().plusDays(1)
        );

        // Simular que el servicio de comandos devuelve un Optional<Expense>
        when(expenseCommandService.handle(any(CreateExpenseCommand.class))).thenReturn(Optional.of(expense));

        // Act & Assert: Realizar el POST y verificar que la respuesta sea la esperada
        mockMvc.perform(post("/api/v1/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Expense\", \"amount\": 100.00, \"userId\": 1, \"groupId\": 1, \"dueDate\": \"2024-12-01\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Expense"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }
    @Test
    void testGetExpenseById() throws Exception {
        // Arrange: Simulamos el gasto a retornar
        Expense expense = new Expense("Test Expense", BigDecimal.valueOf(100.00), new UserInformation(), new Group(), LocalDate.now().plusDays(1));

        // Simulamos el comportamiento del servicio de consultas
        when(expenseQueryService.handle(any(GetExpenseByIdQuery.class))).thenReturn(Optional.of(expense));

        // Act & Assert: Realizar el GET y verificar que la respuesta es la esperada
        mockMvc.perform(get("/api/v1/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Expense"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }
    @Test
    void testUpdateExpense() throws Exception {
        // Arrange: Crear un recurso de gasto actualizado
        UpdateExpenseResource updateExpenseResource = new UpdateExpenseResource("Updated Expense", BigDecimal.valueOf(150.00), LocalDate.now().plusDays(2));

        // Simular el gasto actualizado
        Expense expense = new Expense(
                "Updated Expense",
                BigDecimal.valueOf(150.00),
                new UserInformation(),  // Simulamos el usuario
                new Group(),  // Simulamos el grupo
                LocalDate.now().plusDays(2)
        );

        // Simulamos que el servicio retorna el gasto actualizado
        when(expenseCommandService.handle(any(UpdateExpenseCommand.class))).thenReturn(Optional.of(expense));

        // Act & Assert: Realizar el PUT y verificar la respuesta
        mockMvc.perform(put("/api/v1/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Expense\", \"amount\": 150.00, \"dueDate\": \"2024-12-02\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Expense"))
                .andExpect(jsonPath("$.amount").value(150.00));
    }
    @Test
    void testDeleteExpense() throws Exception {
        // Arrange: Simular que la eliminación no genera problemas
        doNothing().when(expenseCommandService).handle(any(DeleteExpenseCommand.class));

        // Act & Assert: Realizar el DELETE y verificar que la respuesta sea 204 No Content
        mockMvc.perform(delete("/api/v1/expenses/expenseId/1"))
                .andExpect(status().isNoContent());
    }
}