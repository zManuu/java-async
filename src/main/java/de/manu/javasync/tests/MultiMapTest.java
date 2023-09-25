package de.manu.javasync.tests;

import de.manu.javasync.ITest;
import de.manu.javasync.Main;
import de.manu.javasync.module.Pair;

import java.util.HashMap;

public class MultiMapTest implements ITest {

    @Override
    public void test(Main main) {
        var map = new HashMap<String, Pair<Integer, String>>();
        map.put("test-user", new Pair<>(1, "Manu"));
        Main.print(map.get("test-user"));
    }

}
