package rubberduck.org.sportinksystemalt.shared.common.service.queue;

@FunctionalInterface
public interface QueueMessageHandler<T> {
    void handle(T message);
}
