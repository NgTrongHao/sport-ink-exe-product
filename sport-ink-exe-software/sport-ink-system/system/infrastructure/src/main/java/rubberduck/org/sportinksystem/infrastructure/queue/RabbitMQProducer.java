package rubberduck.org.sportinksystem.infrastructure.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import rubberduck.org.sportinksystem.core.applicationservice.queue.QueueProducer;

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
