package de.twometer.evolution.entity;

import de.twometer.evolution.core.Context;
import de.twometer.evolution.genetics.DNA;
import de.twometer.evolution.genetics.Gender;
import de.twometer.evolution.genetics.Reproduction;
import de.twometer.evolution.world.Tile;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.function.Predicate;

public class EntityCrab extends EntityLiving {

    private String model;
    private Models models = Context.getInstance().getModels();

    private Random random = new Random((long) (Math.random() * 98358732));

    private static final String GESTATION_DURATION = "gestation_duration";
    private static final String SENSORY_DISTANCE = "sensory_distance";
    private static final String REPRODUCTIVE_URGE = "reproductive_urge";
    private static final String SPEED = "speed";
    private static final String DESIRABILITY = "desirability";

    private State state = State.Idle;
    private float hunger;
    private float thirst;

    private EntityCrab matingTarget;
    private int pregnantTicks = -1;
    private int matingTicks = 0;

    private int age = 0;

    public EntityCrab(Gender gender, DNA dna) {
        super(gender, dna);
        init();
    }

    public EntityCrab(Gender gender) {
        super(gender, new DNA());
        getDna().registerGene(GESTATION_DURATION, 100.0f, Gender.Female);
        getDna().registerGene(SENSORY_DISTANCE, 24.0f);
        getDna().registerGene(REPRODUCTIVE_URGE, 0.4f);
        getDna().registerGene(SPEED, 1.0f);
        getDna().registerGene(DESIRABILITY, 0.5f);
        init();
        age = 150;
    }

    private void init() {
        model = getGender() == Gender.Male ? "crab.obj" : "crab-female.obj";
        speed = getDna().getGene(SPEED).getValue();
    }

    @Override
    public void render() {
        models.draw(model, position, rotation, age < 150 ? 0.2f : 0.5f);
    }

    @Override
    public void tick() {
        age++;


        hunger += lerp(0.001f, 0.005f, random.nextFloat()) * getDna().getGene(SPEED).getValue();
        thirst += lerp(0.001f, 0.005f, random.nextFloat()) * getDna().getGene(SPEED).getValue();

        if (pregnantTicks >= 0)
            pregnantTicks++;

        float reproductiveUrge = getDna().getGene(REPRODUCTIVE_URGE).getValue();

        if (state == State.Idle) {
            if (hunger > 0.3)
                state = State.SearchFood;
            else if (thirst > 0.3)
                state = State.SearchWater;

            if (reproductiveUrge > hunger && reproductiveUrge > thirst && getGender() == Gender.Male)
                state = State.SearchMate;
        }

        if (hunger > 1.0 || thirst > 1.0)
            die();

        switch (state) {
            case Idle:
                break;
            case SearchFood:
                if (!hasTarget()) {
                    EntityPlant closestFood = findClosestEntity(EntityPlant.class, e -> true);
                    if (closestFood != null)
                        moveTo(closestFood.position).then(() -> {
                            hunger = 0;
                            state = State.Idle;
                            closestFood.die();
                        });
                }
                break;
            case SearchWater:
                if (!hasTarget()) {
                    Vector3f closestWater = findClosestTile(Tile.WATER);
                    if (closestWater != null)
                        moveTo(closestWater).then(() -> {
                            thirst = 0;
                            state = State.Idle;
                        });
                }
                break;
            case SearchMate:
                if (hunger > reproductiveUrge || thirst > reproductiveUrge)
                    state = State.Idle;

                if (!hasTarget()) {
                    EntityCrab closestMate = findClosestEntity(EntityCrab.class, e -> e.getGender() == Gender.Female);
                    if (closestMate != null) {
                        boolean mated = closestMate.requestMating(this);
                        if (mated)
                            moveTo(closestMate.position).then(() -> {
                                state = State.Mating;
                                matingTarget = closestMate;
                            });
                    }
                }
                break;
            case Mating:
                if (getGender() == Gender.Male && matingTicks == 0) {
                    matingTarget.setPregnant(this);
                    matingTarget.state = State.Mating;
                }
                matingTicks++;
                if (matingTicks > 30) {
                    state = State.Idle;
                }
                break;
        }

        if (pregnantTicks > getDna().getGene(GESTATION_DURATION).getValue()) {
            giveBirth();
        }

        System.out.println(getGender() + ", " + state + "; " + hunger + "; " + thirst + "; " + hasTarget() + "; " + pregnantTicks);

        if (!hasTarget() && state != State.Waiting && state != State.Mating) {
            float range = getDna().getGene(SENSORY_DISTANCE).getValue();
            float dx = random(-range, range);
            float dz = random(-range, range);
            Vector3f newPosition = clampToWorldEdge(new Vector3f(position.x + dx, position.y, position.z + dz));
            moveTo(newPosition);
        }
    }

    private <T extends BaseEntity> T findClosestEntity(Class<T> entityClass, Predicate<T> predicate) {
        float maxDistance = getDna().getGene(SENSORY_DISTANCE).getValue();

        T closestEntity = null;
        float closestDistance = maxDistance;

        for (BaseEntity entity : Context.getInstance().getWorld().getEntities()) {
            if (entity.getClass() == entityClass && predicate.test((T) entity)) {
                float dist = new Vector3f(position).sub(entity.position).length();
                if (dist < closestDistance) {
                    closestEntity = (T) entity;
                    closestDistance = dist;
                }
            }
        }

        return closestDistance > maxDistance ? null : closestEntity;
    }


    private Vector3f findClosestTile(byte tile) {
        float maxDistance = getDna().getGene(SENSORY_DISTANCE).getValue();

        float closestDistance = maxDistance;

        Vector3f closestTile = null;
        Vector3f myTile = new Vector3f((float) Math.floor(position.x), 0, (float) Math.floor(position.z));

        for (float i = -maxDistance; i <= maxDistance; i++) {
            for (float j = -maxDistance; j <= maxDistance; j++) {
                int xo = (int) (myTile.x + i);
                int zo = (int) (myTile.z + j);
                if (Context.getInstance().getWorld().getTile(xo, zo) == tile) {
                    float dist = (float) Math.sqrt(i * i + j * j);
                    if (dist < closestDistance) {
                        closestDistance = dist;
                        closestTile = new Vector3f(xo, 1, zo);
                    }
                }
            }
        }

        return closestDistance > maxDistance ? null : closestTile;
    }

    private void giveBirth() {
        EntityCrab baby = Reproduction.createOffspring(this, matingTarget);
        Context.getInstance().getWorld().getEntities().add(baby);
        pregnantTicks = -1;
    }

    private void setPregnant(EntityCrab father) {
        this.pregnantTicks = 0;
        this.matingTarget = father;
    }

    private boolean requestMating(EntityCrab father) {
        if (father.getGender() == getGender() || pregnantTicks != -1 || state == State.Waiting || state == State.Mating)
            return false;
        float chance = lerp(0.219f, 1f, father.getDna().getGene(DESIRABILITY).getValue());
        if (random.nextFloat() > chance)
            return false;
        stopMoving();
        state = State.Waiting;
        return true;
    }

    private Vector3f clampToWorldEdge(Vector3f vector3f) {
        vector3f.x = clamp(vector3f.x, 0, Context.getInstance().getWorld().getLength());
        vector3f.z = clamp(vector3f.z, 0, Context.getInstance().getWorld().getDepth());
        return vector3f;
    }

    private float lerp(float a, float b, float f) {
        return a + f * (b - a);
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
        SearchMate,
        Mating,
        Waiting
    }

}
