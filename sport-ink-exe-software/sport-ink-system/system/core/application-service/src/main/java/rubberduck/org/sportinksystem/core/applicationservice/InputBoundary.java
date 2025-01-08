package rubberduck.org.sportinksystem.core.applicationservice;

public interface InputBoundary<T, R> {
    R execute(T request);
}
