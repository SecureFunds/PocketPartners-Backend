package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllExpensesByDueDate;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@EnableScheduling
public class ExpensesNotificationService {

    private final ExpenseQueryService expenseQueryService;
    private final TwilioSmsService twilioSmsService;

    @Autowired
    public ExpensesNotificationService(ExpenseQueryService expenseQueryService, TwilioSmsService twilioSmsService) {
        this.expenseQueryService = expenseQueryService;
        this.twilioSmsService = twilioSmsService;
    }

    public void sendPaymentReminders() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        // Obtener pagos que vencen mañana
        List<Expense> upcomingPayments = expenseQueryService.handle(new GetAllExpensesByDueDate(tomorrow));
        // Obtener pagos que están atrasados (vencen hoy)
        List<Expense> overduePayments = expenseQueryService.handle(new GetAllExpensesByDueDate(today));

        // Enviar recordatorios para pagos próximos
        for (Expense expense : upcomingPayments) {
            sendReminder(expense, "Tu pago vence mañana: " + expense.getName() + " por " + expense.getAmount());
        }

        // Enviar recordatorios para pagos atrasados
        for (Expense expense : overduePayments) {
            sendReminder(expense, "Tu pago está atrasado: " + expense.getName() + " por " + expense.getAmount());
        }
    }

    private void sendReminder(Expense expense, String message) {
        UserInformation userInformation = expense.getUserInformation();
        String phoneNumber = userInformation.getPhoneNumber(); // Asegúrate de que el número de teléfono esté disponible

        twilioSmsService.sendReminder(phoneNumber, message);
    }
}
