package de.manu.javasync.tests;

import de.manu.javasync.ITest;
import de.manu.javasync.Main;

public class ThreadInterruptionTest implements ITest {

    @Override
    public void test(Main main) {
        var thread = new TestThread1();
        thread.start();

        try {
            Thread.sleep(5000);
            thread.interrupt();
        } catch (Exception ex) {
            Main.print("Sleep to interrupt the test-thread was interrupted. Probably not the wanted behavior?");
        }
    }

    private static class TestThread1 extends Thread {

        @Override
        public void run() {
            var interrupted = false;
            while (!interrupted) {
                try {
                    Main.print("Hello there");
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    Main.print("1000ms delay was interrupted");
                    interrupted = true;
                }
            }
        }
    }

}
