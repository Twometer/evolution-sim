package de.twometer.evolution.render;

import de.twometer.evolution.core.Context;
import de.twometer.evolution.core.ILifecycle;
import de.twometer.evolution.core.LifecycleManager;
import de.twometer.evolution.entity.BaseEntity;
import de.twometer.evolution.entity.EntityCrab;
import de.twometer.evolution.font.FontRenderer;
import de.twometer.evolution.genetics.Gender;
import de.twometer.evolution.genetics.Gene;
import de.twometer.evolution.gl.Camera;
import de.twometer.evolution.gl.GameWindow;
import de.twometer.evolution.shaders.MainShader;
import de.twometer.evolution.world.World;
import de.twometer.evolution.world.WorldGenerator;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer implements ILifecycle {

    private GameWindow gameWindow;

    private Camera camera;

    private MainShader mainShader;

    private World world;

    public MasterRenderer(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    @Override
    public void create() {
        camera = new Camera();
        mainShader = new MainShader();

        world = new World(72, 72);
        Context.getInstance().setWorld(world);

        WorldGenerator generator = new WorldGenerator(world);
        generator.generate();

        world.buildModel();

        glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
        glEnable(GL_DEPTH_TEST);

        gameWindow.hideCursor();
        gameWindow.setCursorPosition(gameWindow.getWidth() / 2.0f, gameWindow.getHeight() / 2.0f);

        for (int i = 0; i < 10; i++) {

            EntityCrab crab = new EntityCrab(Gender.Male);
            crab.setPosition(new Vector3f((float) Math.random() * world.getLength(), 1, (float) Math.random() * world.getDepth()));
            world.getEntities().add(crab);

            EntityCrab crab2 = new EntityCrab(Gender.Female);
            crab2.setPosition(new Vector3f((float) Math.random() * world.getLength(), 1, (float) Math.random() * world.getDepth()));
            world.getEntities().add(crab2);

        }
    }

    public void render() {
        long lastTick = System.currentTimeMillis();
        FontRenderer renderer = new FontRenderer("lucida");
        LifecycleManager.initialize(renderer);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        while (!gameWindow.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Matrix4f viewMatrix = camera.calcViewMatrix();
            Context.getInstance().setViewMatrix(viewMatrix);

            Matrix4f projMatrix = new Matrix4f().perspective((float) Math.toRadians(70f), (float) gameWindow.getWidth() / gameWindow.getHeight(), 0.1f, 500.0f);
            Context.getInstance().setProjectionMatrix(projMatrix);

            Context.getInstance().getGuiMatrix().setOrtho2D(0.0f, gameWindow.getWidth(), gameWindow.getHeight(), 0.0f);

            mainShader.bind();
            mainShader.setModelMatrix(new Matrix4f().scale(1.0f));
            mainShader.setViewMatrix(viewMatrix);
            mainShader.setProjectionMatrix(projMatrix);

            world.render();

            for (BaseEntity entity : world.getEntities()) {
                entity.render();
                entity.update();
            }

            if (System.currentTimeMillis() - lastTick > 100) {
                for (BaseEntity entity : world.getEntities()) entity.tick();
                lastTick = System.currentTimeMillis();
            }

            int total = 0;
            int males = 0;
            int females = 0;

            Map<String, Float> averageGenes = new HashMap<>();

            for (BaseEntity entity : world.getEntities()) {
                if (entity instanceof EntityCrab) {
                    EntityCrab crab = (EntityCrab) entity;
                    total++;
                    if (crab.getGender() == Gender.Male) males++;
                    else females++;

                    for (Map.Entry<String, Gene> gene : crab.getDna().getGenes().entrySet()) {
                        if (averageGenes.containsKey(gene.getKey()))
                            averageGenes.put(gene.getKey(), averageGenes.get(gene.getKey()) + gene.getValue().getValue());
                        else
                            averageGenes.put(gene.getKey(), gene.getValue().getValue());
                    }
                }
            }

            glDisable(GL_DEPTH_TEST);
            List<String> lines = new ArrayList<>();
            lines.add("Population: " + total);
            lines.add("Males: " + males);
            lines.add("Females: " + females);

            for (Map.Entry<String, Float> gene : averageGenes.entrySet()) {
                lines.add(gene.getKey() + ": " + gene.getValue() / (float) total);
            }

            lines.add("");

            if (total == 0)
                lines.add("SPECIES BECAME EXTINCT");
            else if (females == 0 || males == 0)
                lines.add("SPECIES DYING");
            else if (females <= 5 || males <= 5)
                lines.add("SPECIES PROBABLY GOING EXTINCT");
            else
                lines.add("SPECIES IS HEALTHY");

            int y = 0;
            for (String line : lines) {
                if (line.length() != 0)
                    renderer.draw(line, 10f, y, 0.35f, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
                y += 20;
            }


            glEnable(GL_DEPTH_TEST);

            handleControls();

            gameWindow.update();
        }
    }

    private void handleControls() {
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

        Vector2f delta = gameWindow.getCursorPosition().sub(new Vector2f(gameWindow.getWidth() / 2, gameWindow.getHeight() / 2));
        camera.getAngle().add(new Vector2f(-delta.x * 0.04f, -delta.y * 0.04f));

        gameWindow.setCursorPosition(gameWindow.getWidth() / 2f, gameWindow.getHeight() / 2f);
    }

    @Override
    public void destroy() {
        mainShader.destroy();
    }
}
