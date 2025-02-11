package rubberduck.org.sportinksystemalt.shared.service.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer<T> implements QueueProducer<T> {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String queueName, T message) {
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.printf("Message sent to queue %s: %s%n", queueName, message);
    }
}
