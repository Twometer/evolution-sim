package de.twometer.evolution.entity;

import de.twometer.evolution.core.Context;
import de.twometer.evolution.core.ILifecycle;
import de.twometer.evolution.mesh.Model;
import de.twometer.evolution.res.Loader;
import de.twometer.evolution.shaders.ModelShader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class Models implements ILifecycle {

    private static final String[] MODELS = {"crab.obj", "plant.obj", "tree.obj"};

    private Map<String, Model> modelMap = new HashMap<>();

    private ModelShader modelShader;

    @Override
    public void create() {
        for (String model : MODELS)
            modelMap.put(model, Loader.loadObj(model));
        modelShader = new ModelShader();
    }

    void draw(String model, Vector3f position, float rotation, float scale) {
        modelShader.bind();
        modelShader.setViewMatrix(Context.getInstance().getViewMatrix());
        modelShader.setProjectionMatrix(Context.getInstance().getProjectionMatrix());
        modelShader.setModelMatrix(new Matrix4f().translate(position).rotate(rotation, new Vector3f(0, 1, 0)).scale(scale));
        modelMap.get(model).draw();
    }

    @Override
    public void destroy() {
        for (Model model : modelMap.values())
            model.destroy();
        modelShader.destroy();
    }
}
