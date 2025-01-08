package rubberduck.org.sportinksystem.core.applicationservice.queue;

@FunctionalInterface
public interface QueueMessageHandler<T> {
    void handle(T message);
}
