package b4u.pocketpartners.backend.operations.domain.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class TwilioSmsService {

    private final String ACCOUNT_SID = "--------------------";
    private final String AUTH_TOKEN = "-----------------------";
    private final String FROM_PHONE = "-----------------------";

    public TwilioSmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }


    private String formatPhoneNumber(String phoneNumber) {

        if (!phoneNumber.startsWith("+")) {

            phoneNumber = "+51" + phoneNumber;
        }
        return phoneNumber;
    }


    public void sendReminder(String toPhoneNumber, String dueDate) {

        String formattedPhoneNumber = formatPhoneNumber(toPhoneNumber);
        String messageBody = String.format("Recordatorio:");

        Message.creator(
                new PhoneNumber(formattedPhoneNumber),
                new PhoneNumber(FROM_PHONE),
                messageBody
        ).create();
    }
}
