package de.twometer.evolution;

import de.twometer.evolution.shaders.MainShader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.text.NumberFormat;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class EvolutionMain {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;

    public static void main(String[] args) {

        GameWindow gameWindow = new GameWindow(WIDTH, HEIGHT, "Evolution Simulator | by Twometer");
        LifecycleManager.initialize(gameWindow);

        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        glEnable(GL_DEPTH_TEST);

        World world = new World(64, 64);
        world.buildModel();

        Camera camera = new Camera();

        MainShader shader = new MainShader();
        gameWindow.hideCursor();
        gameWindow.setCursorPosition(WIDTH / 2.0f, HEIGHT / 2.0f);
        Vector2f lastPos = gameWindow.getCursorPosition();
        while (!gameWindow.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            shader.bind();
            shader.setModelMatrix(new Matrix4f().identity());
            shader.setViewMatrix(camera.calcViewMatrix());
            shader.setProjectionMatrix(new Matrix4f().perspective((float) Math.toRadians(70f), (float) WIDTH / HEIGHT, 0.1f, 500.0f));
            world.render();

            Vector2f delta = gameWindow.getCursorPosition().sub(lastPos);
            camera.getAngle().add(new Vector2f(-delta.x * 0.04f, -delta.y * 0.04f));

            float yaw = (float) Math.toRadians(camera.getAngle().x);

            float dx = (float) Math.sin(yaw) * 0.2f;
            float dz = (float) Math.cos(yaw) * 0.2f;

            float dx2 = (float) Math.sin(yaw + Math.PI / 2) * 0.2f;
            float dz2 = (float) Math.cos(yaw + Math.PI / 2) * 0.2f;

            if (gameWindow.isKeyPressed(GLFW_KEY_W))
                camera.getPosition().add(new Vector3f(dx, 0.0f, dz));

            if (gameWindow.isKeyPressed(GLFW_KEY_A))
                camera.getPosition().add(new Vector3f(dx2, 0.0f, dz2));


            if (gameWindow.isKeyPressed(GLFW_KEY_S))
                camera.getPosition().sub(new Vector3f(dx, 0.0f, dz));


            if (gameWindow.isKeyPressed(GLFW_KEY_D))
                camera.getPosition().sub(new Vector3f(dx2, 0.0f, dz2));


            if (gameWindow.isKeyPressed(GLFW_KEY_SPACE))
                camera.getPosition().add(new Vector3f(0f, 0.2f, 0f));
            if (gameWindow.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
                camera.getPosition().add(new Vector3f(0f, -0.2f, 0f));


            gameWindow.setCursorPosition(400, 300);
            lastPos = gameWindow.getCursorPosition();
            gameWindow.update();
        }

        LifecycleManager.shutdown();
    }

}
