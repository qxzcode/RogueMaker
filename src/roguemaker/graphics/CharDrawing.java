package roguemaker.graphics;

import org.lwjgl.BufferUtils;
import roguemaker.Main;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * @author Quinn Tucker
 */
public class CharDrawing {
    
    private CharDrawing() {} // not allowed to be instantiated
    
    public static void init() {
        // init quad VAO
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        int positionVbo = glGenBuffers();
        FloatBuffer fb = BufferUtils.createFloatBuffer(2 * 4);
        fb.put(0.0f).put(0.0f);
        fb.put(1.0f).put(0.0f);
        fb.put(1.0f).put(1.0f);
        fb.put(0.0f).put(1.0f);
        fb.flip();
        glBindBuffer(GL_ARRAY_BUFFER, positionVbo);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glVertexAttribPointer(Main.shader.inPositionLoc, 2, GL_FLOAT, false, 0, 0L);
        glEnableVertexAttribArray(Main.shader.inPositionLoc);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        
        // init texture
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer components = BufferUtils.createIntBuffer(1);
        byte[] dataArr = Main.loadResource("resources/font.png");
        ByteBuffer data = BufferUtils.createByteBuffer(dataArr.length);
        data.put(dataArr).rewind();
        data = Objects.requireNonNull(stbi_load_from_memory(data, width, height, components, 1));
        int imgWidth  = width.get();
        int imgHeight = height.get();
        charWidth  = imgWidth  / 16;
        charHeight = imgHeight / 16;
        
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, imgWidth, imgHeight, 0, GL_RED, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);
        
        // set texScale uniform
        glUniform2f(Main.shader.texScaleLoc, 1/16f, 1/16f);
    }
    
    public static void drawChar(int x, int y, char c, float[] fgColor, float[] bgColor) {
        glBindTexture(GL_TEXTURE_2D, texture);
        glBindVertexArray(vao);
        glUniform2f(Main.shader.offsetLoc, x, y);
        glUniform2f(Main.shader.texOffsetLoc, c%16, c/16);
        glUniform3fv(Main.shader.fgColorLoc, fgColor);
        glUniform3fv(Main.shader.bgColorLoc, bgColor);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
    
    private static int vao, texture;
    
    public static int charWidth, charHeight;
    
}
