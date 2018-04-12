package roguemaker.game.entity;

import java.awt.Color;
import java.util.Optional;

/**
 * @author Quinn Tucker
 */
public abstract class Attribute {
    
    public void update() {}
    
    public Optional<Character> getChar(Optional<Character> prev) {
        return prev;
    }
    
    public Optional<Color> getColor(Optional<Color> prev) {
        return prev;
    }
    
    protected Entity entity;
    
}
