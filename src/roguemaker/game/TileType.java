package roguemaker.game;

import java.awt.*;
import java.util.Random;

/**
 * @author Quinn Tucker
 */
public abstract class TileType {
    
    public TileType() {
        
    }
    
    public abstract char getChar(Location loc);
    public abstract Color getFGColor(Location loc);
    public abstract Color getBGColor(Location loc);
    
    public static class Location {
        public Location(Level level) {
            this.level = level;
            this.rand = new Random();
        }
        
        public Level level;
        public int x, y;
        public Random rand;
    }
    
}
