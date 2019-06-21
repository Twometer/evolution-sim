package de.twometer.evolution.core;

import de.twometer.evolution.entity.Models;
import de.twometer.evolution.world.World;
import org.joml.Matrix4f;

public class Context implements ILifecycle {

    public static final float SPEED_MODIIFIER = 1;

    private static Context instance = new Context();

    private World world;

    private Models models;

    private Matrix4f viewMatrix;

    private Matrix4f projectionMatrix;

    private Matrix4f guiMatrix = new Matrix4f();

    public static Context getInstance() {
        return instance;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Models getModels() {
        return models;
    }

    public void setModels(Models models) {
        this.models = models;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public Matrix4f getGuiMatrix() {
        return guiMatrix;
    }

    @Override
    public void create() {
        models = new Models();
        LifecycleManager.initialize(models);
    }

    @Override
    public void destroy() {

    }
}
