package de.twometer.evolution.world;

import de.twometer.evolution.entity.BaseEntity;
import de.twometer.evolution.mesh.CubeFace;
import de.twometer.evolution.mesh.CubeMesh;
import de.twometer.evolution.mesh.Model;
import org.joml.Vector3f;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class World {

    // X Direction
    private int length;

    // Z Direction
    private int depth;

    // Tile data
    private byte[] data;

    // Noise data for smooth coloring
    private float[] offsets;

    // Baked model
    private Model model;

    // All the entities in this world
    private List<BaseEntity> entities = new CopyOnWriteArrayList<>();

    public World(int length, int depth) {
        this.length = length;
        this.depth = depth;
        this.data = new byte[length * depth];
        this.offsets = new float[length * depth];
    }

    public void buildModel() {
        CubeMesh mesh = new CubeMesh(80000 * 2);
        for (int x = 0; x < length; x++) {
            for (int z = 0; z < depth; z++) {
                Vector3f pos = new Vector3f(x, 0, z);
                Vector3f color = getColor(x, z);
                Vector3f colorA = new Vector3f(color).mul(0.6f);
                Vector3f colorB = new Vector3f(color).mul(0.3f);

                float height = getTile(x, z) == Tile.WATER ? 0.75f : 1.0f;

                if (shouldRenderFace(x, z, x + 1, z)) mesh.putFace(pos, colorA, CubeFace.PosX, height);
                if (shouldRenderFace(x, z, x, z + 1)) mesh.putFace(pos, colorB, CubeFace.PosZ, height);
                if (shouldRenderFace(x, z, x - 1, z)) mesh.putFace(pos, colorA, CubeFace.NegX, height);
                if (shouldRenderFace(x, z, x, z - 1)) mesh.putFace(pos, colorB, CubeFace.NegZ, height);

                mesh.putFace(pos, color, CubeFace.PosY, height);
                mesh.putFace(pos, color, CubeFace.NegY, height);
            }
        }

        this.model = Model.create3d(mesh, GL_TRIANGLES);
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

    public byte getTile(int x, int z) {
        int i = z * depth + x;
        if (i < 0 || i >= data.length)
            return 0;
        return data[i];
    }

    private float getOffset(int x, int z) {
        int i = z * depth + x;
        if (i < 0 || i >= data.length)
            return 0;
        return offsets[i];
    }

    void setTile(int x, int z, byte tile, float offset) {
        data[z * depth + x] = tile;
        offsets[z * depth + x] = offset;
    }

    private Vector3f getColor(int x, int z) {
        int tile = getTile(x, z);
        switch (tile) {
            case Tile.SAND:
                return rgb(255, 253, 125).sub(rgb(14, 51, 43).mul(1 - getOffset(x, z) * 2));
            case Tile.GRASS:
                return rgb(124, 165, 52).sub(rgb(74, 39, -3).mul(getOffset(x, z)));
            case Tile.WATER:
                return rgb(118, 192, 253).sub(rgb(26, 27, -1).mul(getOffset(x, z) * 3));
        }
        return new Vector3f(0, 0, 0);
    }

    private Vector3f rgb(int r, int g, int b) {
        return new Vector3f(r / 255.0f, g / 255.0f, b / 255.0f);
    }

    public int getLength() {
        return length;
    }

    public int getDepth() {
        return depth;
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }
}
