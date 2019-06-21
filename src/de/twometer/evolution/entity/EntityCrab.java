package de.twometer.evolution.entity;

import de.twometer.evolution.core.Context;
import de.twometer.evolution.genetics.DNA;
import de.twometer.evolution.genetics.Gender;
import org.joml.Random;
import org.joml.Vector3f;

public class EntityCrab extends EntityLiving {

    private static final String GESTATION_DURATION = "gestation_duration";
    private static final String SENSORY_DISTANCE = "sensory_distance";
    private static final String REPRODUCTIVE_URGE = "reproductive_urge";
    private static final String SPEED = "speed";
    private static final String DESIRABILITY = "desirability";

    private static final String MODEL = "crab.obj";

    private Models models = Context.getInstance().getModels();

    private State state = State.Idle;

    private Random random = new Random((long) (Math.random() * 98358732));

    public EntityCrab(Gender gender, DNA dna) {
        super(gender, dna);
    }

    public EntityCrab(Gender gender) {
        super(gender, new DNA());
        getDna().registerGene(GESTATION_DURATION, 10.0f, Gender.Female);
        getDna().registerGene(SENSORY_DISTANCE, 6.0f);
        getDna().registerGene(REPRODUCTIVE_URGE, 1.0f);
        getDna().registerGene(SPEED, 1.0f);
        getDna().registerGene(DESIRABILITY, 1.0f);
    }

    @Override
    public void render() {
        models.draw(MODEL, position, rotation, 0.5f);
    }

    @Override
    public void update() {
        super.update();
        /*if (state == State.Idle && !hasTarget()) {
            float maxDist = getDna().getGene(SENSORY_DISTANCE).getValue() / 2f;
            float xd = random(-maxDist, maxDist);
            float zd = random(-maxDist, maxDist);
           // moveTo(clampToWorldEdge(new Vector3f(position).add(xd, 0, zd)));
        }*/
    }

    private Vector3f clampToWorldEdge(Vector3f vector3f) {
        vector3f.x = clamp(vector3f.x, 0, Context.getInstance().getWorld().getLength());
        vector3f.z = clamp(vector3f.z, 0, Context.getInstance().getWorld().getDepth());
        return vector3f;
    }

    private float clamp(float val, float min, float max) {
        if (val < min) val = min;
        if (val > max) val = max;
        return val;
    }

    private float random(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    private enum State {
        Idle,
        SearchWater,
        SearchFood,
        SearchingMate
    }

}
