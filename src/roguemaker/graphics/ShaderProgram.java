package roguemaker.graphics;

import roguemaker.Main;

import java.awt.*;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Quinn Tucker
 */
public class ShaderProgram {
    
    public ShaderProgram() {
        this(Main.loadResource("resources/shader.vs"), Main.loadResource("resources/shader.fs"));
    }
    
    private ShaderProgram(byte[] vsSrc, byte[] fsSrc) {
        program = glCreateProgram();
        int vs = compileShader(GL_VERTEX_SHADER, vsSrc);
        int fs = compileShader(GL_FRAGMENT_SHADER, fsSrc);
        glAttachShader(program, vs);
        glAttachShader(program, fs);
        glLinkProgram(program);
        glDeleteShader(vs);
        glDeleteShader(fs);
        
        int linked = glGetProgrami(program, GL_LINK_STATUS);
        String programLog = glGetProgramInfoLog(program);
        if (programLog.trim().length() > 0) {
            System.err.println(programLog);
        }
        if (linked == 0) {
            throw new AssertionError("Failed to link program");
        }
        
        inPositionLoc = glGetAttribLocation(program, "position");
        scaleLoc = glGetUniformLocation(program, "scale");
        offsetLoc = glGetUniformLocation(program, "offset");
        texScaleLoc = glGetUniformLocation(program, "texScale");
        texOffsetLoc = glGetUniformLocation(program, "texOffset");
        fgColorLoc = glGetUniformLocation(program, "fgColor");
        bgColorLoc = glGetUniformLocation(program, "bgColor");
    }
    
    private static int compileShader(int type, byte[] src) {
        int shader = glCreateShader(type);
        glShaderSource(shader, new String(src));
        glCompileShader(shader);
        
        int isCompiled = glGetShaderi(shader, GL_COMPILE_STATUS);
        String shaderLog = glGetShaderInfoLog(shader);
        if (shaderLog.trim().length() > 0) {
            System.err.println(shaderLog);
        }
        if (isCompiled == 0) {
            throw new AssertionError("Failed to compile shader");
        }
        return shader;
    }
    
    public void use() {
        glUseProgram(program);
    }
    
    public void free() {
        glDeleteProgram(program);
    }
    
    private final int program;
    public final int inPositionLoc;
    public final int scaleLoc, offsetLoc, texScaleLoc, texOffsetLoc, fgColorLoc, bgColorLoc;
    
}
