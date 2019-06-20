package de.twometer.evolution.gl;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow implements ILifecycle {

    private long window;

    private int width;

    private int height;

    private String title;

    public GameWindow(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    @Override
    public void create() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_SAMPLES, 8);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        GL.createCapabilities();
    }

    @Override
    public void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    /**
     * Get the current position of the mouse cursor relative to the top left corner of the frame
     *
     * @return The current position of the mouse cursor
     */
    public Vector2f getCursorPosition() {
        double[] xPos = new double[1];
        double[] yPos = new double[1];
        glfwGetCursorPos(window, xPos, yPos);
        return new Vector2f((float) xPos[0], (float) yPos[0]);
    }

    public void setCursorPosition(float x, float y) {
        glfwSetCursorPos(window, x, y);
    }

    public void hideCursor() {
        glfwSetCursor(window, 0);
    }

    /**
     * Check if a key button is pressed
     *
     * @param key The GLFW constant of the key to check
     * @return Whether the key is pressed
     */
    public boolean isKeyPressed(int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }
}
