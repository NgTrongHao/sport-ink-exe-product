package rubberduck.org.sportinksystem.core.applicationservice.queue;

public interface QueueProducer<T> {
    void produce(String queueName, T message);
}
