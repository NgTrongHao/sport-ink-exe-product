package rubberduck.org.sportinksystemalt.shared.service.queue;

import jakarta.annotation.PreDestroy;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class RabbitMQConsumer {

    private final ExecutorService executorService;
    private final QueueMessageHandler<String> messageHandler;

    public RabbitMQConsumer(ExecutorService executorService, QueueMessageHandler<String> messageHandler) {
        this.executorService = executorService;
        this.messageHandler = messageHandler;
    }

    @PreDestroy
    public void shutdownExecutor() {
        executorService.shutdown();
    }

    @RabbitListener(queues = "email-queue")
    public void consume(String encryptedMessage) {
        executorService.submit(() -> {
            try {
                messageHandler.handle(encryptedMessage);
            } catch (Exception e) {
                System.err.printf("Failed to process message: %s%n", e.getMessage());
            }
        });
    }
}
