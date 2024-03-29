package de.twometer.evolution.res;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Utility for loading resources embedded in the jar file
 */
public class ResourceLoader {

    /**
     * Loads a string from a file embedded in the jar resources
     *
     * @param path The path to the file
     * @return The contents of the file
     * @throws IOException Will be thrown if the file does not exist, or the reading fails for any other reason.
     */
    public static String loadString(String path) throws IOException {
        BufferedReader reader = new BufferedReader(openReader(path));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            builder.append(line).append("\n");
        return builder.toString();
    }

    /**
     * Opens a stream reader to a file embedded in the jar resources
     *
     * @param path The path to the file
     * @return A reader on the content stream of the file
     * @throws IOException Will be thrown if the file does not exist
     */
    public static InputStreamReader openReader(String path) throws IOException {
        return new InputStreamReader(openStream(path), StandardCharsets.UTF_8);
    }

    /**
     * Loads an image from a file embedded in the jar resources
     *
     * @param path The path to the image
     * @return The image
     * @throws IOException Will be thrown if the file does not exist, or the image decoding fails
     */
    static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(openStream(path));
    }

    private static InputStream openStream(String path) throws IOException {
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
        if (stream == null)
            throw new IOException(String.format("Failed to load '%s' from resources", path));
        return stream;
    }

}