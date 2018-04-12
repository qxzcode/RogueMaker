package roguemaker.game.entity;

import roguemaker.game.Level;

import java.awt.Color;
import java.util.*;

/**
 * @author Quinn Tucker
 */
public class Entity {
    
    public Entity(Level level, int x, int y, Attribute... attrs) {
        this.level = Objects.requireNonNull(level);
        this.x = x;
        this.y = y;
        this.attributes = new ArrayList<>(Arrays.asList(attrs));
        for (Attribute a : attributes) {
            a.entity = this;
        }
    }
    
    public void update() {
        for (Attribute a : attributes) {
            a.update();
        }
    }
    
    public char getChar() {
        Optional<Character> c = Optional.empty();
        for (Attribute a : attributes) {
            c = a.getChar(c);
        }
        if (!c.isPresent())
            throw new java.lang.IllegalStateException("Entity has no char defined");
        return c.get();
    }
    
    public Color getColor() {
        Optional<Color> c = Optional.empty();
        for (Attribute a : attributes) {
            c = a.getColor(c);
        }
        if (!c.isPresent())
            throw new java.lang.IllegalStateException("Entity has no color defined");
        return c.get();
    }
    
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
    public final List<Attribute> attributes;
    
    public static final int RIGHT=0, UP=1, LEFT=2, DOWN=3;
    
}
