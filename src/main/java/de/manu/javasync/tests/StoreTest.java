package de.manu.javasync.tests;

import de.manu.javasync.ITest;
import de.manu.javasync.Main;
import de.manu.javasync.module.Store;

import java.io.Serializable;

public class StoreTest implements ITest {

    private final TestStore defaultTestStore = new TestStore(1, "Manu");

    @Override
    public void test(Main main) {
        var store = new Store<>(defaultTestStore, Store.StoreUriProvider.defaultAppDataProviderWithSimpleName);
        Main.print(store.get());
        store.get().setName("MANUUUUUUU");
        store.save();
        Main.print(store.get());
    }

    private static class TestStore implements Serializable {
        private final int id;
        private String name;

        public TestStore(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
