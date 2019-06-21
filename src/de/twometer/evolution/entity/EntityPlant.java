package de.twometer.evolution.entity;

import de.twometer.evolution.core.Context;

public class EntityPlant extends BaseEntity {

    private static final String MODEL = "plant.obj";

    private Models models = Context.getInstance().getModels();

    public EntityPlant() {
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
