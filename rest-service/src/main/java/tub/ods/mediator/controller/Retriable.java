package tub.ods.mediator.controller;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Uninterruptibles;

public class Retriable<T> implements Callable<T> {
    private static final Logger log = LoggerFactory.getLogger(Retriable.class);

    private final Callable<T> callable;
    private String errorMessage = "Operation failed";
    private Set<Class<? extends Exception>> retryOn = Collections.singleton(Exception.class);
    private int delaySec = 5;

    public Retriable(Callable<T> callable) {
        this.callable = callable;
    }

    public static Retriable<?> wrapTask(Task task) {
        return new Retriable<>(() -> {
            task.run();
            return null;
        });
    }

    public static <T> Retriable<T> wrap(Callable<T> callable) {
        return new Retriable<T>(callable);
    }
    
    @SafeVarargs
    public final Retriable<T> retryOn(Class<? extends Exception>... exceptionClass) {
        retryOn = ImmutableSet.copyOf(exceptionClass);
        return this;
    }

    public Retriable<T> withErrorMessage(String errorMessage) {
        this.errorMessage = Preconditions.checkNotNull(errorMessage);
        return this;
    }

    public Retriable<T> withDelaySec(int delaySec) {
        Preconditions.checkArgument(delaySec > 0);
        this.delaySec = delaySec;
        return this;
    }

    @Override
    public T call() {
        T result;
        while (true) {
            try {
                result = callable.call();
                break;
            } catch (Throwable e) {
                if (retryOn.stream().noneMatch(c -> getAllCauses(e).anyMatch(c::isInstance))) {
                    throw Throwables.propagate(e);
                }
                log.warn(errorMessage + ", retry in " + delaySec + " sec: " + e.toString());
                Uninterruptibles.sleepUninterruptibly(delaySec, TimeUnit.SECONDS);
            }
        }
        return result;
    }
    
    private Stream<Throwable> getAllCauses(Throwable t) {
        Stream.Builder<Throwable> builder = Stream.builder();
        Set<Throwable> causes = new LinkedHashSet<>(4);
        while (causes.add(t)) {
            builder.accept(t);
        }
        return builder.build();
    }
    
    public interface Task {
        void run() throws Exception;
    }
}
