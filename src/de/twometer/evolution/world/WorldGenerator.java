package de.twometer.evolution.world;

import de.twometer.evolution.entity.EntityPlant;
import de.twometer.evolution.entity.EntityTree;
import org.joml.Random;
import org.joml.Vector3f;

public class WorldGenerator {

    private World world;

    private OpenSimplexNoise noise;

    private OpenSimplexNoise secondNoise;

    private Random random;

    public WorldGenerator(World world) {
        this.world = world;
        this.noise = new OpenSimplexNoise(652132);
        this.secondNoise = new OpenSimplexNoise(123411);
        this.random = new Random((long) (Math.random() * 83471));
    }

    public void generate() {
        float offset = 0.3f;
        for (int x = 0; x < world.getLength(); x++)
            for (int z = 0; z < world.getDepth(); z++) {
                double n = noise.eval(x / 15d, z / 15d);
                double n2 = secondNoise.eval(x / 7d, z / 7d);
                double n3 = secondNoise.eval(x / 10d, z / 10d);
                if (n > offset)
                    world.setTile(x, z, Tile.WATER, (float) Math.abs(n) / 4 * 3 + (float) Math.abs(n2) / 4 * 1 - offset);
                else if (Math.abs(n - offset) < 0.2)
                    world.setTile(x, z, Tile.SAND, (float) Math.abs(n));
                else
                    world.setTile(x, z, Tile.GRASS, (float) Math.abs(n) / 6 * 5 + (float) Math.abs(n3) / 6 * 1 - offset);
            }

        populateEntities();
    }

    private void populateEntities() {
        for (int x = 0; x < world.getLength(); x++)
            for (int z = 0; z < world.getDepth(); z++) {
                byte tile = world.getTile(x, z);
                if (tile == Tile.GRASS) {
                    if (random.nextFloat() > 0.99f)
                        world.getEntities().add(new EntityTree().setPosition(new Vector3f(x, 1, z)));
                    else if (random.nextFloat() > 0.99f)
                        world.getEntities().add(new EntityPlant().setPosition(new Vector3f(x, 1, z)));
                }

            }
    }

    public void regrow() {
        for (int x = 0; x < world.getLength(); x++)
            for (int z = 0; z < world.getDepth(); z++) {
                byte tile = world.getTile(x, z);
                if (tile == Tile.GRASS && random.nextFloat() > 0.989f)
                    world.getEntities().add(new EntityPlant().setPosition(new Vector3f(x, 1, z)));
            }
    }

}
