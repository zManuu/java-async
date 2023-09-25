package de.manu.javasync;

import java.util.HashMap;

public class MultiMapTest implements ITest {

    @Override
    public void test(Main main) {
        var map = new HashMap<String, Pair<Integer, String>>();
        map.put("test-user", new Pair<>(1, "Manu"));
        Main.print(map.get("test-user"));
    }

    /**
     * A pair simply describes an object with 2 properties: v1 and v2.
     * It is used to create a map with 2 values.
     * Note: There is no following object that would hold 3 values.
     * Use real types in those cases.
     */
    private static class Pair<V1, V2> {

        private V1 v1;
        private V2 v2;

        public Pair(V1 v1, V2 v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public V1 getV1() { return v1; }
        public V2 getV2() { return v2; }

        public void setV1(V1 v1) { this.v1 = v1; }
        public void setV2(V2 v2) { this.v2 = v2; }

    }

}
