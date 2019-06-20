package de.twometer.evolution.shaders;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class MainShader extends Shader {

    private int modelMatrix;
    private int viewMatrix;
    private int projectionMatrix;

    public MainShader() {
        super("main");
    }

    @Override
    protected void bindUniforms(int program) {
        modelMatrix = glGetUniformLocation(program, "modelMatrix");
        viewMatrix = glGetUniformLocation(program, "viewMatrix");
        projectionMatrix = glGetUniformLocation(program, "projectionMatrix");
    }

    public void setModelMatrix(Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(modelMatrix, false, buffer);
    }

    public void setViewMatrix(Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(viewMatrix, false, buffer);
    }

    public void setProjectionMatrix(Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(projectionMatrix, false, buffer);
    }

}
