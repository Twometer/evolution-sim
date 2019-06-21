package de.twometer.evolution.entity;

import de.twometer.evolution.core.Context;
import org.joml.Vector3f;

public abstract class BaseEntity {

    Vector3f position;

    float rotation;

    public abstract void render();

    public abstract void update();

    public abstract void tick();

    void die() {
        Context.getInstance().getWorld().getEntities().remove(this);
    }

    public BaseEntity setPosition(Vector3f position) {
        this.position = position;
        return this;
    }


    public Vector3f getPosition() {
        return position;
    }
}
