package de.manu;

import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        var main = new Main();

        Set<ITest> tests = Set.of(
                new Test1()
        );

        tests.forEach(e -> e.test(main));
        main.keepAlive();
    }

    public static void print(String message) {
        var threadName = Thread.currentThread().getName();
        var sb = new StringBuilder()
                .append("[")
                .append(threadName)
                .append("] ")
                .append(message);

        System.out.println(sb);
    }

    /**
     * Keeps the program alive until it is closed manually so that async operations can be performed.
     */
    private void keepAlive() {
        var _t = new Scanner(System.in).next();
    }

}