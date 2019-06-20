package de.twometer.evolution;

import static org.lwjgl.opengl.GL30.*;

/**
 * A model is a mesh that has been uploaded
 * to the GPU and is ready for rendering
 */
public class Model {

    private int vao;

    private int vertexBuffer;
    private int colorBuffer;

    private int vertices;

    private int primitiveType;

    private Model(int vao, int vertexBuffer, int colorBuffer, int vertices, int primitiveType) {
        this.vao = vao;
        this.vertexBuffer = vertexBuffer;
        this.colorBuffer = colorBuffer;
        this.vertices = vertices;
        this.primitiveType = primitiveType;
    }

    public static Model create(Mesh mesh, int primitiveType) {
        mesh.getVertices().flip();
        mesh.getColors().flip();

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, mesh.getVertices(), GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        int colorBuffer = -1;
        if (mesh.getColorCount() > 0) {
            colorBuffer = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, colorBuffer);
            glBufferData(GL_ARRAY_BUFFER, mesh.getColors(), GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        return new Model(vao, vertexBuffer, colorBuffer, mesh.getVertexCount(), primitiveType);
    }

    public void destroy() {
        glDeleteBuffers(vertexBuffer);
        glDeleteBuffers(colorBuffer);
        glDeleteVertexArrays(vao);
    }

    public void draw() {
        boolean hasColors = colorBuffer != -1;

        glBindVertexArray(vao);

        glEnableVertexAttribArray(0);
        if (hasColors) glEnableVertexAttribArray(1);

        glDrawArrays(primitiveType, 0, vertices);

        if (hasColors) glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }

}