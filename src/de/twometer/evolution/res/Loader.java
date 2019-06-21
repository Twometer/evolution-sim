package de.twometer.evolution.res;

import de.twometer.evolution.mesh.Mesh;
import de.twometer.evolution.mesh.Model;
import de.twometer.evolution.obj.WavefrontParser;

import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public class Loader {

    public static int loadShader(String vertPath, String fragPath) {
        try {
            int vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
            int fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);

            String vertexShaderCode = ResourceLoader.loadString(vertPath);
            String fragmentShaderCode = ResourceLoader.loadString(fragPath);

            System.out.println("Compiling vertex shader");
            glShaderSource(vertexShaderId, vertexShaderCode);
            glCompileShader(vertexShaderId);
            checkShaderError(vertexShaderId);

            System.out.println("Compiling fragment shader");
            glShaderSource(fragmentShaderId, fragmentShaderCode);
            glCompileShader(fragmentShaderId);
            checkShaderError(fragmentShaderId);

            int programId = glCreateProgram();
            glAttachShader(programId, vertexShaderId);
            glAttachShader(programId, fragmentShaderId);
            glLinkProgram(programId);

            // After the shader program is linked, the shader sources can be cleaned up
            glDetachShader(programId, vertexShaderId);
            glDetachShader(programId, fragmentShaderId);
            glDeleteShader(vertexShaderId);
            glDeleteShader(fragmentShaderId);

            return programId;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Model loadObj(String model) {
        WavefrontParser parser = new WavefrontParser("models/", model);
        try {
            Mesh mesh = parser.createMesh();
            Model mdl = Model.create(mesh, GL_TRIANGLES);
            mesh.destroy();
            return mdl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkShaderError(int shaderId) {
        String log = glGetShaderInfoLog(shaderId);
        if (log.length() > 0)
            System.out.println(log);
    }

}
