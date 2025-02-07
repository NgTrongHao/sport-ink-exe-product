package rubberduck.org.sportinksystemalt.shared.common.service.queue;

public interface QueueProducer<T> {
    void produce(String queueName, T message);
}
