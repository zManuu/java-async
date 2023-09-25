package de.manu.javasync.tests;

import de.manu.javasync.ITest;
import de.manu.javasync.Main;

import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest implements ITest {

    @Override
    public void test(Main main) {
        // strings
        // filtering, collect to set
        var wordsStream = Stream.of("Hello", "world", "says", "manu");
        var wordsStreamed = wordsStream.filter(e -> e.length() < 5).collect(Collectors.toSet());
        Main.print(wordsStreamed);

        // IntStream: min, max, avg
        // orElse, orElseGet, orElseThrow
        IntSupplier intSupplier = () -> 5;

        var intStream = IntStream.range(100, 1000);
        var intStream2 = IntStream.range(100, 1000);
        var intStream3 = IntStream.range(100, 1000);
        var intStreamMin = intStream.min().orElse(1);
        var intStreamMax = intStream2.max().orElseGet(intSupplier);
        var intStreamAvg = intStream3.average().orElseThrow(() -> new RuntimeException("TEST"));

        Main.print("intStreamMin: " + intStreamMin);
        Main.print("intStreamMax: " + intStreamMax);
        Main.print("intStreamAvg: " + intStreamAvg);

        // TestUser
        // streamBuilder, filter, map, predicate
        Predicate<TestUser> testUserPredicateAdmin = (TestUser user) -> user.getRole() == TestUserRole.ADMIN;
        Predicate<TestUser> testUserPredicateModerator = (TestUser user) -> user.getRole() == TestUserRole.MODERATOR;

        var testUserStream = Stream.<TestUser>builder()
                .add(new TestUser("Manu", TestUserRole.ADMIN))
                .add(new TestUser("Paco", TestUserRole.USER))
                .add(new TestUser("Pascal", TestUserRole.MODERATOR))
                .build();

        var teamNames = testUserStream.filter(testUserPredicateAdmin.or(testUserPredicateModerator))
                .map(TestUser::getName)
                .collect(Collectors.toUnmodifiableSet());

        Main.print("teamNames: " + teamNames);
    }

    private enum TestUserRole { ADMIN, MODERATOR, USER }
    private static class TestUser {
        private static int idGen = 0;
        private final int id;
        private final String name;
        private final TestUserRole role;

        public TestUser(String name, TestUserRole role) {
            this.id = idGen++;
            this.name = name;
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public TestUserRole getRole() {
            return role;
        }
    }

}
