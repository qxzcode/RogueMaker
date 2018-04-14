package roguemaker.game;

import java.awt.*;
import java.util.Random;

/**
 * @author Quinn Tucker
 */
public abstract class TileType {
    
    public TileType() {
        
    }
    
    public abstract boolean isSolid();
    public abstract boolean isOpaque();
    
    public abstract char getChar(Location loc);
    public abstract Color getFGColor(Location loc);
    public abstract Color getBGColor(Location loc);
    
    public static class Location {
        public Location(Level level) {
            this.level = level;
            this.rand = new Random();
        }
        
        public void setSeed(long seed) {
            rand.setSeed(seed);
            rand.nextDouble(); // stir up the entropy
        }
        
        public Level level;
        public int x, y;
        public Random rand;
    }
    
}
