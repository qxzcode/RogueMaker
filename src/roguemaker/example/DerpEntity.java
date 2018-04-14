package roguemaker.example;

import roguemaker.game.entity.Entity;
import roguemaker.game.Level;

import java.awt.*;

/**
 * @author Quinn Tucker
 */
public class DerpEntity extends Entity {
    
    public DerpEntity(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void update() {
        tryMove((int)(Math.random()*4));
    }
    
    @Override
    public char getChar() {
        return 'd';
    }
    
    @Override
    public Color getColor() {
        return Color.BLUE;
    }
    
}
