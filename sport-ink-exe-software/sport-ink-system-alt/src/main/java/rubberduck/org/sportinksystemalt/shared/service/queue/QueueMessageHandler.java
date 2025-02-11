package rubberduck.org.sportinksystemalt.shared.service.queue;

@FunctionalInterface
public interface QueueMessageHandler<T> {
    void handle(T message);
}
