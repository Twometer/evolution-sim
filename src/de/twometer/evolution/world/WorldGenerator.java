package de.twometer.evolution.world;

public class WorldGenerator {

    private World world;

    private OpenSimplexNoise noise;

    public WorldGenerator(World world) {
        this.world = world;
        this.noise = new OpenSimplexNoise(123456);
    }

    public void generate() {
        float offset = 0.3f;
        for (int x = 0; x < world.getLength(); x++)
            for (int z = 0; z < world.getDepth(); z++) {
                double n = noise.eval(x / 20d, z / 20d);
                if (n > offset)
                    world.setTile(x, z, Tile.WATER);
                else if (Math.abs(n - offset) < 0.1 + n * 0.2f)
                    world.setTile(x, z, Tile.SAND);
                else
                    world.setTile(x, z, Tile.GRASS);
            }
    }

}
