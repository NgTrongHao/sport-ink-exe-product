package rubberduck.org.sportinksystem.infrastructure.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystem.core.applicationservice.mail.MailSender;
import rubberduck.org.sportinksystem.core.applicationservice.queue.MessageEncryptor;
import rubberduck.org.sportinksystem.core.applicationservice.queue.QueueProducer;
import rubberduck.org.sportinksystem.core.domain.entity.message.EmailMessage;

import java.util.Map;

@Service
public class QueueBasedMailSender implements MailSender {
    private final QueueProducer<String> queueProducer;
    private final ObjectMapper objectMapper;
    private final MessageEncryptor messageEncryptor;

    public QueueBasedMailSender(QueueProducer<String> queueProducer, ObjectMapper objectMapper, MessageEncryptor messageEncryptor) {
        this.queueProducer = queueProducer;
        this.objectMapper = objectMapper;
        this.messageEncryptor = messageEncryptor;
    }

    @Override
    public void sendWelcomeEmail(String email, String name) {
        sendEmail(email, name, "Welcome to Our Service", "welcome-email", Map.of("name", name));
    }

    @Override
    public void sendEmailVerificationEmail(String email, String name, String token) {
        sendEmail(email, name, "Email Verification", "email-verification-email", Map.of("name", name, "token", token));
    }

    @Override
    public void sendPasswordResetEmail(String email, String name, String token) {

    }

    private void sendEmail(String to, String name, String subject, String template, Map<String, Object> variables) {
        EmailMessage emailMessage = new EmailMessage(to, name, subject, template, variables);
        try {
            queueProducer.produce("email-queue", messageEncryptor.encrypt(objectMapper.writeValueAsString(emailMessage)));
        } catch (Exception e) {
            System.err.printf("Failed to send email to queue. Error: %s%n", e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
