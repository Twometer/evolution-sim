package de.twometer.evolution.mesh;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Mesh {

    private FloatBuffer vertices;

    private FloatBuffer colors;

    private int vertexCount;

    private int colorCount;

    public Mesh(int vertexCapacity) {
        vertices = MemoryUtil.memAllocFloat(vertexCapacity * 3);
        colors = MemoryUtil.memAllocFloat(vertexCapacity * 3);
    }

    public void putVertex(float x, float y, float z) {
        vertices.put(x);
        vertices.put(y);
        vertices.put(z);
        vertexCount++;
    }

    public void putColor(float r, float g, float b) {
        colors.put(r);
        colors.put(g);
        colors.put(b);
        colorCount++;
    }

    int getVertexCount() {
        return vertexCount;
    }

    int getColorCount() {
        return colorCount;
    }

    FloatBuffer getVertices() {
        return vertices;
    }

    FloatBuffer getColors() {
        return colors;
    }

    public void destroy() {
        MemoryUtil.memFree(vertices);
        MemoryUtil.memFree(colors);
    }

}
