package b4u.pocketpartners.backend.operations.domain.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class TwilioSmsService {

    private final String ACCOUNT_SID = "------"; // Replace with your actual Twilio Account SID
    private final String AUTH_TOKEN = "------";   // Replace with your actual Twilio Auth Token
    private final String FROM_PHONE = "----"; // Your Twilio phone number

    public TwilioSmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    // Método para formatear el número de teléfono
    private String formatPhoneNumber(String phoneNumber) {
        // Asegúrate de que el número tenga el prefijo "+"
        if (!phoneNumber.startsWith("+")) {
            // Prepend el código de país (Perú, +51 en este caso)
            phoneNumber = "+51" + phoneNumber;
        }
        return phoneNumber;
    }

    // Método para enviar recordatorios
    public void sendReminder(String toPhoneNumber, String dueDate) {
        // Formatear el número de teléfono antes de enviarlo
        String formattedPhoneNumber = formatPhoneNumber(toPhoneNumber);
        String messageBody = String.format("Recordatorio:");

        Message.creator(
                new PhoneNumber(formattedPhoneNumber), // Número de destino (formateado)
                new PhoneNumber(FROM_PHONE),          // Número de origen (Twilio)
                messageBody                           // Cuerpo del mensaje
        ).create();
    }
}
