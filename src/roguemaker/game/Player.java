package roguemaker.game;

import roguemaker.game.entity.Entity;

/**
 * @author Quinn Tucker
 */
public interface Player {
    
    boolean onFrame();
    
    Entity getEntity();
    
}
