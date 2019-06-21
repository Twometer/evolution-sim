package de.twometer.evolution.genetics;

import de.twometer.evolution.entity.EntityCrab;
import org.joml.Vector3f;

import java.util.Map;
import java.util.Random;

public class Reproduction {

    private static Random random = new Random();

    public static EntityCrab createOffspring(EntityCrab mother, EntityCrab father) {
        if (mother.getGender() == father.getGender())
            throw new IllegalStateException("Cannot produce offspring");

        Gender newGender = randomGender();

        DNA newDna = new DNA();
        for (Map.Entry<String, Gene> entry : mother.getDna().getGenes().entrySet()) {
            float newVal = inherit(entry.getValue().getValue(), father.getDna().getGene(entry.getKey()).getValue());
            newDna.registerGene(entry.getKey(), newVal);
        }
        EntityCrab newCrab = new EntityCrab(newGender, newDna);
        newCrab.setPosition(new Vector3f(mother.getPosition()));

        return newCrab;
    }

    private static float inherit(float a, float b) {
        float gene = random.nextDouble() < 0.5 ? a : b;
        if (random.nextDouble() < 0.2) {
            float mutateAmount = (float) random.nextGaussian() * 0.4f;
            gene += mutateAmount;
        }
        return gene;
    }

    private static Gender randomGender() {
        return random.nextBoolean() ? Gender.Female : Gender.Male;
    }
}
