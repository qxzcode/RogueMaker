package roguemaker.game;

import roguemaker.RogueMaker;
import roguemaker.game.entity.Entity;
import roguemaker.game.entity.MoveAttribute;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static roguemaker.game.entity.MoveAttribute.*;

/**
 * @author Quinn Tucker
 */
public class BasicPlayer implements Player {
    
    public BasicPlayer(Entity player) {
        this.player = Objects.requireNonNull(player);
    }
    ;
    @Override
    public boolean onFrame() {
        MoveAttribute moveAttr = player.getAttribute(MoveAttribute.class);
        if (RogueMaker.isKeyPressed(GLFW_KEY_0)) {
            return moveAttr.tryMove(UP);
        }
        if (RogueMaker.isKeyPressed(GLFW_KEY_SEMICOLON)) {
            return moveAttr.tryMove(DOWN);
        }
        if (RogueMaker.isKeyPressed(GLFW_KEY_O)) {
            return moveAttr.tryMove(LEFT);
        }
        if (RogueMaker.isKeyPressed(GLFW_KEY_LEFT_BRACKET)) {
            return moveAttr.tryMove(RIGHT);
        }
        if (RogueMaker.isKeyPressed(GLFW_KEY_P)) {
            return true; // rest
        }
        return false;
    }
    
    @Override
    public Entity getEntity() {
        return player;
    }
    
    public Entity player;
    
}
