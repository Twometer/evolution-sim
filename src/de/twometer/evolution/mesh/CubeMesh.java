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

    public void putFace(Vector3f position, Vector3f color, CubeFace face) {
        switch (face) {
            case PosX:
                writeFloats(position, color, positive_x);
                break;
            case PosY:
                writeFloats(position, color, positive_y);
                break;
            case PosZ:
                writeFloats(position, color, positive_z);
                break;
            case NegX:
                writeFloats(position, color, negative_x);
                break;
            case NegY:
                writeFloats(position, color, negative_y);
                break;
            case NegZ:
                writeFloats(position, color, negative_z);
                break;
        }
    }

    private void writeFloats(Vector3f position, Vector3f color, float[] f) {
        for (int i = 0; i < f.length; i += 3) {
            super.putColor(color.x, color.y, color.z);
            super.putVertex(f[i] + position.x, f[i + 1] + position.y, f[i + 2] + position.z);
        }
    }

}
