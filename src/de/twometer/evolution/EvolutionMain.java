package de.twometer.evolution;

import de.twometer.evolution.gl.GameWindow;
import de.twometer.evolution.gl.LifecycleManager;
import de.twometer.evolution.render.MasterRenderer;

public class EvolutionMain {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;

    public static void main(String[] args) {

        GameWindow gameWindow = new GameWindow(WIDTH, HEIGHT, "Evolution Simulator | by Twometer");
        LifecycleManager.initialize(gameWindow);

        MasterRenderer masterRenderer = new MasterRenderer(gameWindow);
        LifecycleManager.initialize(masterRenderer);

        masterRenderer.render();

        LifecycleManager.shutdown();
    }

}
