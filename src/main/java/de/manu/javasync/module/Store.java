package de.manu.javasync.module;

import com.google.gson.Gson;
import de.manu.javasync.Main;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class Store <T extends Serializable> {

    /**
     * A StoreUriProvider provides a store with the path it's state is saved to.
     */
    public interface StoreUriProvider {
        File getFile(Class<?> storeType);

        /**
         * Files are stored in the localappdata directory of the user.
         * They are named after the types name (e.g. de.manu.javasync.tests.StoreTest$TestStore.json).
         * Note: not ready for usage as it links to {@link de.manu.javasync.Config#projectName}.
         */
        public static final StoreUriProvider defaultAppDataProvider = storeType -> {
            var storeName = storeType.getName();
            var localappdataPath = System.getenv("LOCALAPPDATA");
            var localappdataUri = Path.of(localappdataPath);
            var storeDir = localappdataUri.resolve(Main.config.projectName);
            return new File(storeDir.toString(), storeName + ".json");
        };

        /**
         * Files are stored in the localappdata directory of the user.
         * They are named after the types simple name (e.g. TestStore.json).
         * Note: Type named might overlay, in that case, use {@link StoreUriProvider#defaultAppDataProvider}.
         * Note: not ready for usage as it links to {@link de.manu.javasync.Config#projectName}.
         */
        public static final StoreUriProvider defaultAppDataProviderWithSimpleName = storeType -> {
            var storeName = storeType.getSimpleName();
            var localappdataPath = System.getenv("LOCALAPPDATA");
            var localappdataUri = Path.of(localappdataPath);
            var storeDir = localappdataUri.resolve(Main.config.projectName);
            return new File(storeDir.toString(), storeName + ".json");
        };
    }

    private static final Gson gson = new Gson();
    private T val;
    private Path filePath;
    private final T defaultVal;

    /**
     * Initializes a store.
     * @param defaultVal  the default / fallback value
     * @param uriProvider the uri-provider for the store. If set to null,
     */
    public Store(@Nonnull T defaultVal, @Nonnull StoreUriProvider uriProvider) {
        this.defaultVal = defaultVal;

        try {
            // get fileURI from provider
            this.filePath = uriProvider
                    .getFile(defaultVal.getClass())
                    .toPath();
        } catch (Exception e) {
            Main.print(
                    String.format(
                            "Store creation failed! Couldn't construct fileURI. TypeName: %s | provider-test: %s",
                            defaultVal.getClass().getName(),
                            uriProvider.getFile(Object.class)
                    )
            );
            return;
        }

        if (exists())
            load();
        else
            reset();
    }

    /**
     * @return whether the store file exists and is accessible
     */
    private boolean exists() {
        var file = filePath.toFile();
        return file.exists() && file.isFile() && file.canRead() && file.canWrite();
    }

    /**
     * Loads the store values from the file and writes it into the currently cached value.
     * Can be used to roll back the state to the last save.
     * Is called after the initialization automatically.
     */
    public void load() {
        if (!exists()) {
            Main.print("Couldn't load store of type" + defaultVal.getClass().getName() + " because the file doesn't exists yet (might be a permission issue too).");
            return;
        }

        String fileContent;

        try {
            fileContent = Files.readString(filePath);
        } catch (IOException e) {
            Main.print("Couldn't load store of type" + defaultVal.getClass().getName() + " because read failed.");
            return;
        }

        this.val = (T) gson.fromJson(fileContent, defaultVal.getClass());
    }

    /**
     * Saves the currently cached value into the file.
     */
    public void save() {
        var storeJson = gson.toJson(val);

        if (!exists()) {
            var file = filePath.toFile();
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                Main.print("Couldn't save store of type " + defaultVal.getClass().getName() + " (create failed).");
            }
        }

        try {
            Files.writeString(filePath, storeJson);
        } catch (IOException e) {
            Main.print("Couldn't save store of type " + defaultVal.getClass().getName() + " (write failed).");
        }
    }

    /**
     * @return the currently cached store value
     */
    public T get() {
        return val;
    }

    /**
     * Resets the store to the default value passed upon creation.
     */
    public void reset() {
        this.val = this.defaultVal;
        save();
    }

}
