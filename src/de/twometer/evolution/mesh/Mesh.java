package de.twometer.evolution.mesh;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Mesh {

    private FloatBuffer vertices;

    private FloatBuffer colors;

    private FloatBuffer normals;

    private int vertexCount;

    private int colorCount;

    private int normalCount;

    Mesh(int vertexCapacity) {
        this(vertexCapacity, false);
    }

    public Mesh(int vertexCapacity, boolean hasNormals) {
        vertices = MemoryUtil.memAllocFloat(vertexCapacity * 3);
        colors = MemoryUtil.memAllocFloat(vertexCapacity * 3);
        if (hasNormals)
            normals = MemoryUtil.memAllocFloat(vertexCapacity * 3);
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

    public void putNormal(float x, float y, float z) {
        normals.put(x);
        normals.put(y);
        normals.put(z);
        normalCount++;
    }

    int getVertexCount() {
        return vertexCount;
    }

    int getColorCount() {
        return colorCount;
    }

    int getNormalCount() {
        return normalCount;
    }

    FloatBuffer getVertices() {
        return vertices;
    }

    FloatBuffer getColors() {
        return colors;
    }

    FloatBuffer getNormals() {
        return normals;
    }

    public void destroy() {
        MemoryUtil.memFree(vertices);
        MemoryUtil.memFree(colors);
        MemoryUtil.memFree(normals);
    }

}
