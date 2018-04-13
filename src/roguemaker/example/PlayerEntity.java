package roguemaker.example;

import roguemaker.game.Level;
import roguemaker.game.entity.Entity;

import java.awt.Color;

/**
 * @author Quinn Tucker
 */
public class PlayerEntity extends Entity {
    
    public PlayerEntity(Level level, int x, int y) {
            super(level, x, y);
        }
    
    @Override
    public void update() {
        
    }
    
    @Override
    public char getChar() {
        return '@';
    }
    
    @Override
    public Color getColor() {
        return Color.GREEN.darker();
    }
    
}
