package de.twometer.evolution.entity;

import de.twometer.evolution.genetics.DNA;
import de.twometer.evolution.genetics.Gender;
import org.joml.Vector3f;

public abstract class EntityLiving extends BaseEntity {

    private Gender gender;

    private DNA dna;

    private Vector3f target;

    private boolean hasTarget;

    private float yVel = 0.0f;
    private float yAccel = -0.0025f;
    private int groundTicks = 0;

    EntityLiving(Gender gender, DNA dna) {
        this.gender = gender;
        this.dna = dna;
    }

    public Gender getGender() {
        return gender;
    }

    DNA getDna() {
        return dna;
    }

    public void moveTo(Vector3f target) {
        this.target = target;
        this.hasTarget = true;
    }

    @Override
    public void update() {
        if (!hasTarget) return;

        if (position.y <= 1) {
            groundTicks++;
            if (groundTicks > 10) {
                // on ground
                yVel = 0.09f;
                position.y = 1.001f;
            }
        } else {
            groundTicks = 0;
            // not on ground
            position.y += yVel;
            yVel += yAccel;
        }

        Vector3f diff = new Vector3f(
                target.x - position.x,
                target.y - position.y,
                target.z - position.z
        );

        float len = diff.length();
        if (len < 0.1) {
            hasTarget = false;
        }

        rotation = (float) (Math.atan2(diff.z, diff.x));

        position.add(diff.div(len).mul(0.04f));
    }

    public boolean hasTarget() {
        return hasTarget;
    }
}
