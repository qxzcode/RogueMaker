package roguemaker.game.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Quinn Tucker
 */
public class MoveAttribute extends Attribute {
    
    public static abstract class SubAttribute {
        
        public Optional<Boolean> canMoveTo(int x, int y) {
            return Optional.empty();
        }
        
        public MoveAttribute parent;
        
    }
    
    public MoveAttribute(SubAttribute... attrs) {
        attributes = new ArrayList<>(Arrays.asList(attrs));
        for (SubAttribute a : attributes) {
            a.parent = this;
        }
    }
    
    @Override
    public void update() {
        
    }
    
    public boolean tryMove(int direction) {
        int nx = entity.x, ny = entity.y;
        switch (direction % 4) {
            case LEFT:  nx--; break;
            case RIGHT: nx++; break;
            case DOWN:  ny--; break;
            case UP:    ny++; break;
        }
        if (canMoveTo(nx, ny)) {
            entity.x = nx;
            entity.y = ny;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean canMoveTo(int x, int y) {
        return getProperty(attributes, a -> a.canMoveTo(x, y), false);
    }
    
    public final ArrayList<SubAttribute> attributes;
    
    public static final int RIGHT=0, UP=1, LEFT=2, DOWN=3;
    
}
