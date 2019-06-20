package de.twometer.evolution.gl;

import java.util.ArrayList;
import java.util.List;

public class LifecycleManager {

    private static List<ILifecycle> managedObjects = new ArrayList<>();

    public static void initialize(ILifecycle obj) {
        obj.create();
        managedObjects.add(obj);
    }

    public static void shutdown() {
        for (ILifecycle lifecycle : managedObjects)
            lifecycle.destroy();
    }

}
