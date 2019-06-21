package de.twometer.evolution.genetics;

import de.twometer.evolution.entity.EntityLiving;

import java.util.Random;

public class Reproduction {

    private static Random random = new Random();

    public static EntityLiving createOffspring(EntityLiving a, EntityLiving b) {
        if (a.getGender() == b.getGender())
            throw new IllegalStateException("Cannot produce offspring");

        Gender newGender = randomGender();

        return null;
    }

    public static Gender randomGender() {
        return random.nextBoolean() ? Gender.Female : Gender.Male;
    }
}
