package de.twometer.evolution.entity;

import de.twometer.evolution.core.ILifecycle;
import de.twometer.evolution.mesh.Model;
import de.twometer.evolution.res.Loader;

import java.util.HashMap;
import java.util.Map;

public class Models implements ILifecycle {

    private static final String[] MODELS = {"crab.obj", "plant.obj", "tree.obj"};

    private Map<String, Model> modelMap = new HashMap<>();

    @Override
    public void create() {
        for (String model : MODELS)
            modelMap.put(model, Loader.loadObj(model));
    }

    public void draw(String model) {
        modelMap.get(model).draw();
    }

    @Override
    public void destroy() {
        for (Model model : modelMap.values())
            model.destroy();
    }
}
