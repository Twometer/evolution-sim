package de.twometer.evolution.mesh;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Mesh {

    private int vertexCapacity;

    private FloatBuffer vertices;

    private FloatBuffer colors;

    private FloatBuffer normals;

    private FloatBuffer texCoords;

    private int vertexCount;

    private int colorCount;

    private int normalCount;

    private int texCoordCount;

    public Mesh(int vertexCapacity) {
        this.vertexCapacity = vertexCapacity;
        vertices = MemoryUtil.memAllocFloat(vertexCapacity * 3);
        colors = MemoryUtil.memAllocFloat(vertexCapacity * 3);
    }

    public Mesh withNormals() {
        normals = MemoryUtil.memAllocFloat(vertexCapacity * 3);
        return this;
    }

    public Mesh withTexCoords() {
        texCoords = MemoryUtil.memAllocFloat(vertexCapacity * 3);
        return this;
    }

    public void putVertex(float x, float y, float z) {
        vertices.put(x);
        vertices.put(y);
        vertices.put(z);
        vertexCount++;
    }

    public void putVertex(float x, float y) {
        vertices.put(x);
        vertices.put(y);
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

    public void putTexCoord(float u, float v) {
        texCoords.put(u);
        texCoords.put(v);
        texCoordCount++;
    }

    int getVertexCount() {
        return vertexCount;
    }

    int getColorCount() {
        return colorCount;
    }

    int getTexCoordCount() {
        return texCoordCount;
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

    FloatBuffer getTexCoords() {
        return texCoords;
    }

    FloatBuffer getNormals() {
        return normals;
    }

    public void destroy() {
        MemoryUtil.memFree(vertices);
        MemoryUtil.memFree(colors);
        if (normals != null) MemoryUtil.memFree(normals);
        if (texCoords != null) MemoryUtil.memFree(texCoords);
    }

}
