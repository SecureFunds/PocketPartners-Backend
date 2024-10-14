package b4u.pocketpartners.backend.operations.domain.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ScheduledTasks is a Spring component that runs scheduled tasks.
 *
 * @author Fiorella Jarama Peñaloza
 */
@Component
public class ScheduledTasks {

    private final ExpensesNotificationService expensesNotificationService;

    public ScheduledTasks(ExpensesNotificationService expensesNotificationService) {
        this.expensesNotificationService = expensesNotificationService;
    }

    //NO LO ACTIVO A MENOS Q LO NECESITE CONSUME MI DINERITO DE TWILIO
    //@Scheduled(cron = "0 0 9,17 * * ?", zone = "America/Lima") // Ejecutar a las 9:00 AM y 5:00 PM
    //public void sendDailyPaymentReminders() {
        //expensesNotificationService.sendPaymentReminders();
    //}
}