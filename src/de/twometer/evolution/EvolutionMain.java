package de.twometer.evolution;

import static org.lwjgl.opengl.GL11.*;

public class EvolutionMain {

    public static void main(String[] args) {

        GameWindow gameWindow = new GameWindow(800, 600, "Evolution Simulator | by Twometer");
        LifecycleManager.initialize(gameWindow);

        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);

        while (!gameWindow.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            gameWindow.update();
        }

        LifecycleManager.shutdown();
    }

}
