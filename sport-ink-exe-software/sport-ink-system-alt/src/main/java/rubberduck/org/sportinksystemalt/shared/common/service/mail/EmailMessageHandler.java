package rubberduck.org.sportinksystemalt.shared.common.service.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import rubberduck.org.sportinksystemalt.shared.common.service.queue.MessageEncryptor;
import rubberduck.org.sportinksystemalt.shared.common.service.queue.QueueMessageHandler;

import java.io.UnsupportedEncodingException;

@Component
public class EmailMessageHandler implements QueueMessageHandler<String> {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final ObjectMapper objectMapper;
    private final MessageEncryptor messageEncryptor;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailMessageHandler(JavaMailSender mailSender, SpringTemplateEngine templateEngine, ObjectMapper objectMapper, MessageEncryptor messageEncryptor) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.objectMapper = objectMapper;
        this.messageEncryptor = messageEncryptor;
    }

    @Override
    public void handle(String encryptedMessage) {
        try {
            String decryptedMessage = messageEncryptor.decrypt(encryptedMessage);
            rubberduck.org.sportinksystemalt.shared.common.service.mail.EmailMessage emailMessage = objectMapper.readValue(decryptedMessage, EmailMessage.class);
            sendEmail(emailMessage);
        } catch (Exception e) {
            throw new MailSendingException("Failed to handle message", e);
        }
    }

    private void sendEmail(EmailMessage emailMessage) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail, "Sportink System");
        helper.setTo(emailMessage.getEmail());
        helper.setSubject(emailMessage.getSubject());

        Context context = new Context();
        context.setVariables(emailMessage.getVariables());
        String content = templateEngine.process(emailMessage.getTemplate(), context);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
