package de.twometer.evolution.entity;

import org.joml.Vector3f;

public abstract class BaseEntity {

    Vector3f position;

    float rotation;

    public abstract void render();

    public abstract void update();

    public BaseEntity setPosition(Vector3f position) {
        this.position = position;
        return this;
    }


}
