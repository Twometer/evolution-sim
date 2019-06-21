package de.twometer.evolution.entity;

import de.twometer.evolution.core.Context;

public class EntityTree extends BaseEntity {

    private static final String MODEL = "tree.obj";

    private Models models = Context.getInstance().getModels();

    public EntityTree() {
        rotation = (float) (Math.random() * Math.PI * 2);
    }

    @Override
    public void render() {
        models.draw(MODEL, position, rotation, 2.5f);
    }

    @Override
    public void update() {

    }

    @Override
    public void tick() {

    }

}
