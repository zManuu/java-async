package de.manu.javasync.tests;

import de.manu.javasync.ITest;
import de.manu.javasync.Main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest implements ITest {

    private final Runnable taskInstant = () -> Main.print("Wassup from instant-task");

    private final Runnable taskWithTimeout = () -> {
        try {
            Thread.sleep(2000);
            Main.print("Wassup from timeout-task");
        } catch (InterruptedException e) {
            Main.print("Timeout Thread was interrupted.");
        }
    };

    private final Runnable taskInfinitive = () -> {
        int threadRunCount = 0;
        boolean interrupted = false;
        while (!interrupted) {
            threadRunCount++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Main.print("Infinitive Thread was interrupted. Ran a total of " + threadRunCount + " times.");
                interrupted = true;
            }

            Main.print("Thread running (" + threadRunCount + ")...");
        }
    };

    @Override
    public void test(Main main) {
        var executor = Executors.newFixedThreadPool(5);

        // simple execute call
        executor.execute(taskInstant);

        // execute call with timeout
        executor.execute(taskWithTimeout);

        // more complex thread behavior
        executor.execute(taskInfinitive);

        // has no effect on taskInfinitive as shutdown waits for all tasks to finish (the task above won't)
        executor.shutdown();

        terminateAfterTimeout(executor, 1000);
    }

    private void terminateAfterTimeout(ExecutorService executor, int timeout) {
        try {
            Thread.sleep(timeout);
            // interrupts all tasks (doesn't wait for them to finish)
            var currentTasks = executor.shutdownNow();
            Main.print("Tasks the executor had on shutdown: " + currentTasks.size());
        } catch (InterruptedException e) {
            Main.print("terminateAfterTimeout was interrupted");
        }
    }

}
