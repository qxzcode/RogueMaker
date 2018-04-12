package roguemaker;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import roguemaker.example.ExampleMod;
import roguemaker.graphics.Buffer;
import roguemaker.graphics.CharDrawing;
import roguemaker.graphics.ShaderProgram;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Initializing mods");
	    new ExampleMod();
        
        System.out.println("Initializing GLFW");
        initGLFW();
        
        System.out.println("Initializing OpenGL");
        initGL();
        
        System.out.println("Starting loop");
        loop();
        
        System.out.println("Cleaning up");
        cleanup();
    }
    
    private static void initGLFW() {
        // setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();
        
        // init GLFW
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        
        // configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will not be resizable
        
        // create the window
        window = glfwCreateWindow(10, 10, windowTitle, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        
        // setup a key callback
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
            if (action == GLFW_PRESS || action == GLFW_REPEAT) {
                pressedKeys.add(key);
            }
        });
        
        // setup a key callback
        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            if (width > 0 & height > 0) {
                windowWidth = width;
                windowHeight = height;
            }
        });
        
        glfwMakeContextCurrent(window); // make the OpenGL context current
        glfwSwapInterval(1); // enable v-sync
    }
    
    private static void centerWindow() {
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert vidmode != null;
        glfwSetWindowPos(window,
                         (vidmode.width() - windowWidth) / 2,
                         (vidmode.height() - windowHeight) / 2
                        );
    }
    
    private static void initGL() {
        GL.createCapabilities();
        glClearColor(0f, 1f, 1f, 1f);
        
        glDisable(GL_DEPTH_TEST);
        
        shader = new ShaderProgram();
        shader.use();
        
        CharDrawing.init();
        glfwSetWindowSize(window,
                          RogueMaker.getLevel().getWidth()*CharDrawing.charWidth*2,
                          RogueMaker.getLevel().getHeight()*CharDrawing.charHeight*2);
        centerWindow();
    }
    
    private static void loop() {
        // make the window visible
        glfwShowWindow(window);
        
        Buffer buffer = new Buffer(RogueMaker.getLevel().getWidth(), RogueMaker.getLevel().getHeight());
        
        long lastFPSTime = System.currentTimeMillis();
        int fps = 0;
        while (!glfwWindowShouldClose(window)) {
            // update
            fps++;
            long now = System.currentTimeMillis();
            if (now - lastFPSTime >= 1000) {
                glfwSetWindowTitle(window, windowTitle+" ("+fps+" FPS)");
                lastFPSTime = now;
                fps = 0;
            }
            
            RogueMaker.getLevel().update();
            buffer.update(RogueMaker.getLevel());
            
            // draw and stuff
            pressedKeys.clear();
            glfwPollEvents();
            glViewport(0, 0, windowWidth, windowHeight);
            glUniform2f(shader.scaleLoc,
                        2.0f / RogueMaker.getLevel().getWidth(),
                        2.0f / RogueMaker.getLevel().getHeight());
            glClear(GL_COLOR_BUFFER_BIT);
            
            buffer.draw();
            
            glfwSwapBuffers(window);
        }
    }
    
    private static void cleanup() {
        // free the shader program
        shader.free();
        
        // free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        
        // terminate GLFW and free the error callback
        glfwTerminate();
        GLFWErrorCallback cb = glfwSetErrorCallback(null);
        assert cb != null;
        cb.free();
    }
    
    public static byte[] loadResource(String path) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = in.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toByteArray();
    }
    
    private static long window;
    private static String windowTitle = "RogueMaker";
    private static int windowWidth, windowHeight;
    public static ShaderProgram shader;
    
    static Set<Integer> pressedKeys = new HashSet<>();
    
}
