package roguemaker.game;

import java.awt.Color;

/**
 * @author Quinn Tucker
 */
public abstract class Entity {
    
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void update();
    public abstract char getChar();
    public abstract Color getColor();
    
    public int x, y;
    
}
