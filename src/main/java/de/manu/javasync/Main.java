package de.manu.javasync;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static Gson gson = new Gson();
    public static Config config;

    public static void main(String[] args) {
        var main = new Main();

        try {
            initConfig();
        } catch (Exception e) {
            System.out.println("Config couldn't be loaded.");
            return;
        }

        Set<ITest> tests = Set.of(
                new ThreadInterruptionTest(),
                new CompletableFutureTest(),
                new StreamTest(),
                new StoreTest(),
                new MultiMapTest()
        );

        tests.forEach(e -> {
            var simpleClassName = e.getClass().getSimpleName();
            var testName = simpleClassName.substring(0, simpleClassName.length() - 4);
            if (config.tests.contains(testName)) {
                print("Testing " + testName + "...");
                e.test(main);
            }
        });
        main.keepAlive();
    }

    private static void initConfig() throws Exception {
        var configUrl = Main.class.getResource("/config.json");

        if (configUrl == null)
            throw new RuntimeException("configUrl most not be null");

        var configPath = Paths.get(configUrl.toURI());
        var configContent = Files.readString(configPath);
        config = gson.fromJson(configContent, Config.class);
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
     * Uses gson to serialize the object. Be sure the passed object is serializable.
     */
    public static void print(Object obj) {
        print(gson.toJson(obj));
    }

    /**
     * Keeps the program alive until it is closed manually so that async operations can be performed.
     */
    private void keepAlive() {
        var _t = new Scanner(System.in).next();
    }

}