package rubberduck.org.sportinksystem.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class QueueConfig {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email-queue", true);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue("sms-queue", true);
    }

    @Bean
    public Queue pushNotificationQueue() {
        return new Queue("push-notification-queue", true);
    }

    @Bean
    public List<String> queues() {
        return List.of(
                "email-queue",
                "sms-queue",
                "push-notification-queue"
        );
    }
}
