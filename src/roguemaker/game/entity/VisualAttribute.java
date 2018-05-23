package roguemaker.game.entity;

import java.awt.*;
import java.util.Optional;

/**
 * @author Quinn Tucker
 */
public class VisualAttribute extends Attribute {
    
    public VisualAttribute(char c, Color color) {
        this.c = c;
        this.color = color;
    }
    
    @Override
    public Optional<Character> getChar() {
        return Optional.of(c);
    }
    
    @Override
    public Optional<Color> getColor() {
        return Optional.of(color);
    }
    
    protected char c;
    protected Color color;
    
}
