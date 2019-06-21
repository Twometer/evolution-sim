package de.twometer.evolution;

import de.twometer.evolution.core.Context;
import de.twometer.evolution.core.LifecycleManager;
import de.twometer.evolution.gl.GameWindow;
import de.twometer.evolution.render.MasterRenderer;

public class EvolutionMain {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow(WIDTH, HEIGHT, "Evolution Simulator | by Twometer");
        LifecycleManager.initialize(gameWindow);

        LifecycleManager.initialize(Context.getInstance());

        MasterRenderer masterRenderer = new MasterRenderer(gameWindow);
        LifecycleManager.initialize(masterRenderer);

        masterRenderer.render();

        LifecycleManager.shutdown();
    }

}
