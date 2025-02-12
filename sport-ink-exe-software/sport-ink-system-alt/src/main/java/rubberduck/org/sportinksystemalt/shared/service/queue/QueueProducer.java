package rubberduck.org.sportinksystemalt.shared.service.queue;

public interface QueueProducer<T> {
    void produce(String queueName, T message);
}
