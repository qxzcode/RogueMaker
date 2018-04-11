package roguemaker.game;

import roguemaker.RogueMaker;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @author Quinn Tucker
 */
public class PlayerEntity extends Entity {
    
    public PlayerEntity(Level level, int x, int y) {
            super(level, x, y);
        }
    
    @Override
    public void update() {
        if (RogueMaker.isKeyPressed(GLFW_KEY_0))
            tryMove(UP);
        if (RogueMaker.isKeyPressed(GLFW_KEY_SEMICOLON))
            tryMove(DOWN);
        if (RogueMaker.isKeyPressed(GLFW_KEY_O))
            tryMove(LEFT);
        if (RogueMaker.isKeyPressed(GLFW_KEY_LEFT_BRACKET))
            tryMove(RIGHT);
    }
    
    @Override
    public char getChar() {
        return 'P';
    }
    
    @Override
    public Color getColor() {
        return Color.GREEN.darker();
    }
    
}
