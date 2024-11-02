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

    
    @Scheduled(cron = "0 35 9,21 * * ?", zone = "America/Lima") 
    public void sendDailyPaymentReminders() {
        expensesNotificationService.sendPaymentReminders();
    }
}