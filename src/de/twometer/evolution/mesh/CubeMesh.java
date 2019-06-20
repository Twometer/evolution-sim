package de.twometer.evolution.mesh;

import org.joml.Vector3f;

public class CubeMesh extends Mesh {

    private static float[] negative_x = {
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 0.0f
    };

    private static float[] negative_y = {
            1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 0.0f,
    };

    private static float[] negative_z = {
            1.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
    };

    private static float[] positive_x = {
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
    };

    private static float[] positive_y = {
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 1.0f,
    };


    private static float[] positive_z = {
            0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f
    };

    public CubeMesh(int vertexCapacity) {
        super(vertexCapacity);
    }

    public void putFace(Vector3f position, Vector3f color, CubeFace face, float height) {
        switch (face) {
            case PosX:
                writeFloats(position, color, positive_x, height);
                break;
            case PosY:
                writeFloats(position, color, positive_y, height);
                break;
            case PosZ:
                writeFloats(position, color, positive_z, height);
                break;
            case NegX:
                writeFloats(position, color, negative_x, height);
                break;
            case NegY:
                writeFloats(position, color, negative_y, height);
                break;
            case NegZ:
                writeFloats(position, color, negative_z, height);
                break;
        }
    }

    private void writeFloats(Vector3f position, Vector3f color, float[] f, float height) {
        for (int i = 0; i < f.length; i += 3) {
            super.putColor(color.x, color.y, color.z);
            super.putVertex(f[i] + position.x, f[i + 1]* height + position.y, f[i + 2] + position.z);
        }
    }

}
