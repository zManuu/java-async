package de.manu.javasync.tests;

import de.manu.javasync.ITest;
import de.manu.javasync.Main;

public class ThreadInterruptionTest implements ITest {

    @Override
    public void test(Main main) {
        var thread = new TestThread1();
        thread.start();
        thread.interrupt();
    }

    private static class TestThread1 extends Thread {

        @Override
        public void run() {
            try {
                Main.print("Testing 1000ms delay that will be interrupted");
                Thread.sleep(1000);
                throw new RuntimeException("1000ms delay wasn't interrupted as expected");
            } catch (InterruptedException interruptedException) {
                Main.print("1000ms delay was interrupted");
            }
        }
    }

}
