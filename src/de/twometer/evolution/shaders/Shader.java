package de.twometer.evolution.shaders;

import de.twometer.evolution.Loader;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    private static final String VERT_SHADER_EXT = ".v.glsl";
    private static final String FRAG_SHADER_EXT = ".f.glsl";

    private int programId;

    Shader(String name) {
        String vertexPath = name + VERT_SHADER_EXT;
        String fragmentPath = name + FRAG_SHADER_EXT;
        programId = Loader.loadShader(vertexPath, fragmentPath);
        initialize();
        System.out.printf("Loaded shader '%s'%n", name);
    }

    private void initialize() {
        bind();
        bindUniforms(programId);
        unbind();
    }

    /**
     * Binds the shader program
     */
    public final void bind() {
        glUseProgram(programId);
    }

    /**
     * Unbinds the shader program
     */
    public final void unbind() {
        glUseProgram(0);
    }

    /**
     * Releases all used native resources
     */
    public final void destroy() {
        glDeleteProgram(programId);
    }

    /**
     * Asks subclasses to initialize themselves and bind their
     * uniform locations
     *
     * @param program The current program id
     */
    protected abstract void bindUniforms(int program);

}
