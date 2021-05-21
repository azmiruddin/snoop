package tub.ods.node.integration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import papyrus.channel.Error;
import papyrus.channel.node.ChannelNodeApplication;

class Util {
    private static final long MAX_WAIT = 600000L;

    static <T> T waitFor(Supplier<T> supplier, Predicate<T> condition) throws InterruptedException {
        AtomicReference<T> reference = new AtomicReference<>();
        waitFor(()-> {
            T t = supplier.get();
            reference.set(t);
            return condition.test(t);
        });
        return reference.get();
    }

    static void waitFor(Supplier<Boolean> condition) throws InterruptedException {
        long start = System.currentTimeMillis();
        long sleep = 10L;
        do {
            long left = (start + MAX_WAIT) - System.currentTimeMillis();
            
            if (left < 0)
                throw new IllegalStateException("Timeout waiting for condition " + condition);

            Thread.sleep(Math.min(sleep, left));
            
            sleep = Math.min(5000L, (long) (1.5 * sleep));
            
        } while (!condition.get());
    }

    static void assertBalance(BigInteger a, BigInteger b) {
        if (a.subtract(b).abs().compareTo(BigInteger.valueOf(10000)) > 0) {
            Assert.assertEquals(a, b);
        }
    }

    static void assertNoError(Error error) {
        Assert.assertEquals(error.getMessage(), 0, error.getStatusValue());
    }

    public static void assertEquals(BigDecimal expected, BigDecimal actual) {
        if (expected.compareTo(actual) != 0) {
            Assert.assertEquals(expected, actual);
        }
    }

    public static void assertEquals(BigInteger expected, BigInteger actual) {
        if (expected.compareTo(actual) != 0) {
            Assert.assertEquals(expected, actual);
        }
    }
}
