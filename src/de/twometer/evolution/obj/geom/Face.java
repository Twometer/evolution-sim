package de.twometer.evolution.obj.geom;

public class Face {

    private VertexRef[] vertices;

    public Face(VertexRef[] vertices) {
        this.vertices = vertices;
    }

    public VertexRef[] getVertices() {
        return vertices;
    }

}
