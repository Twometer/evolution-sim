package de.twometer.evolution.entity;

import de.twometer.evolution.core.Context;
import de.twometer.evolution.genetics.DNA;
import de.twometer.evolution.genetics.Gender;
import de.twometer.evolution.util.Promise;
import org.joml.Vector3f;

public abstract class EntityLiving extends BaseEntity {

    private Gender gender;

    private DNA dna;

    private Vector3f target;

    private boolean hasTarget;

    private float yVel = 0.0f;
    private float yAccel = -0.0025f;
    private int groundTicks = 0;

    private Promise movePromise;

    float speed = 1.0f;

    EntityLiving(Gender gender, DNA dna) {
        this.gender = gender;
        this.dna = dna;
    }

    public Gender getGender() {
        return gender;
    }

    public DNA getDna() {
        return dna;
    }

    Promise moveTo(Vector3f target) {
        this.target = target;
        this.hasTarget = true;
        return (movePromise = new Promise());
    }

    @Override
    public void update() {
        if (!hasTarget) return;

        if (position.y <= 1) {
            // on ground
            groundTicks++;
            if (groundTicks > 10) {
                yVel = 0.09f;
                position.y = 1.001f;
            }
        } else {
            // not on ground
            groundTicks = 0;
            position.y += yVel;
            yVel += yAccel;
        }

        Vector3f diff = new Vector3f(
                target.x - position.x,
                0,
                target.z - position.z
        );
        Vector3f diffNormalized = new Vector3f(diff).normalize();

        float len = diff.length();
        if (len < 1) {
            hasTarget = false;
            if (movePromise != null)
                movePromise.resolve();
        }

        rotation = (float) (Math.atan2(diffNormalized.x, diffNormalized.z) + Math.PI / 2);

        position.add(diffNormalized.mul(0.04f * speed * Context.SPEED_MODIIFIER));
    }

    void stopMoving() {
        hasTarget = false;
    }

    boolean hasTarget() {
        return hasTarget;
    }
}
