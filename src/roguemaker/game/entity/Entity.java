package roguemaker.game.entity;

import roguemaker.game.Level;

import java.awt.Color;
import java.util.Objects;

/**
 * @author Quinn Tucker
 */
public abstract class Entity {
    
    public Entity(Level level, int x, int y) {
        this.level = Objects.requireNonNull(level);
        this.x = x;
        this.y = y;
    }
    
    public abstract void update();
    public abstract char getChar();
    public abstract Color getColor();
    
    public boolean canMoveTo(int x, int y) {
        return !level.getTile(x, y).isSolid();
    }
    
    public boolean tryMove(int direction) {
        int nx = x, ny = y;
        switch (direction % 4) {
            case LEFT:  nx--; break;
            case RIGHT: nx++; break;
            case DOWN:  ny--; break;
            case UP:    ny++; break;
        }
        if (canMoveTo(nx, ny)) {
            x = nx;
            y = ny;
            return true;
        } else {
            return false;
        }
    }
    
    public int x, y;
    public Level level;
    
    public static final int RIGHT=0, UP=1, LEFT=2, DOWN=3;
    
}
