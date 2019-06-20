package de.twometer.evolution.world;

import de.twometer.evolution.mesh.CubeFace;
import de.twometer.evolution.mesh.CubeMesh;
import de.twometer.evolution.mesh.Model;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class World {

    // X Direction
    private int length;

    // Z Direction
    private int depth;

    private byte[] data;

    private Model model;

    public World(int length, int depth) {
        this.length = length;
        this.depth = depth;
        this.data = new byte[length * depth];
        for(int i = 0; i < data.length; i++)
            data[i] = Tile.GRASS;
    }

    public void buildModel() {
        CubeMesh mesh = new CubeMesh(80000 * 2);
        for (int x = 0; x < length; x++) {
            for (int z = 0; z < depth; z++) {
                Vector3f pos = new Vector3f(x, 0, z);
                Vector3f color = getColor(x, z);
                Vector3f colorA = new Vector3f(color).mul(0.6f);
                Vector3f colorB = new Vector3f(color).mul(0.3f);

                float height = getTile(x,z) == Tile.WATER ? 0.6f : 1.0f;

                if (shouldRenderFace(x, z, x + 1, z)) mesh.putFace(pos, colorA, CubeFace.PosX, height);
                if (shouldRenderFace(x, z, x, z + 1)) mesh.putFace(pos, colorB, CubeFace.PosZ, height);
                if (shouldRenderFace(x, z, x - 1, z)) mesh.putFace(pos, colorA, CubeFace.NegX, height);
                if (shouldRenderFace(x, z, x, z - 1)) mesh.putFace(pos, colorB, CubeFace.NegZ, height);

                mesh.putFace(pos, color, CubeFace.PosY, height);
                mesh.putFace(pos, color, CubeFace.NegY, height);
            }
        }

        this.model = Model.create(mesh, GL_TRIANGLES);
    }

    public void render() {
        model.draw();
    }

    private boolean shouldRenderFace(int mx, int mz, int x, int z) {
        // Render borders at the end of the world
        if (x < 0 || z < 0 || x >= length || z >= depth)
            return true;

        // Render borders to water
        return getTile(mx, mz) != Tile.WATER && getTile(x, z) == Tile.WATER;
    }

    private byte getTile(int x, int z) {
        return data[z * depth + x];
    }

    void setTile(int x, int z, byte tile) {
        data[z * depth + x] = tile;
    }

    private Vector3f getColor(int x, int z) {
        int tile = getTile(x, z);
        switch (tile) {
            case Tile.SAND:
                return rgb(255, 253, 113);
            case Tile.GRASS:
                return rgb(176, 200, 49);
            case Tile.WATER:
                return rgb(104, 178, 255);
        }
        return new Vector3f(0, 0, 0);
    }

    private Vector3f rgb(int r, int g, int b) {
        return new Vector3f(r / 255.0f, g / 255.0f, b / 255.0f);
    }

    int getLength() {
        return length;
    }

    int getDepth() {
        return depth;
    }
}
