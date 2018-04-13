package roguemaker.game;

import roguemaker.RogueMaker;
import roguemaker.game.entity.Entity;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static roguemaker.game.entity.Entity.*;

/**
 * @author Quinn Tucker
 */
public class BasicPlayer implements Player {
    
    public BasicPlayer(Entity player) {
        this.player = Objects.requireNonNull(player);
    }
    
    @Override
    public boolean onFrame() {
        if (RogueMaker.isKeyPressed(GLFW_KEY_0)) {
            return player.tryMove(UP);
        }
        if (RogueMaker.isKeyPressed(GLFW_KEY_SEMICOLON)) {
            return player.tryMove(DOWN);
        }
        if (RogueMaker.isKeyPressed(GLFW_KEY_O)) {
            return player.tryMove(LEFT);
        }
        if (RogueMaker.isKeyPressed(GLFW_KEY_LEFT_BRACKET)) {
            return player.tryMove(RIGHT);
        }
        return false;
    }
    
    public Entity player;
    
}
