package de.manu.javasync;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest implements ITest {

    @Override
    public void test(Main main) {
        getWeatherAsync()
                .thenApply(e -> "Extra " + e)
                .thenAccept(Main::print);
    }

    public CompletableFuture<String> getWeatherAsync() {
        try {
            Thread.sleep(1000);
            return CompletableFuture.completedFuture("sunny");
        } catch (InterruptedException e) {
            return CompletableFuture.completedFuture("ERR");
        }
    }

}
