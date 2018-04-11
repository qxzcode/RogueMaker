package roguemaker.graphics;

import roguemaker.game.Level;
import roguemaker.game.TileType;

import java.awt.*;

/**
 * @author Quinn Tucker
 */
public class Buffer {
    
    public Buffer(int width, int height) {
        this.width = width;
        this.height = height;
        charGrid = new char[width][height];
        fgGrid = new float[width][height][3];
        bgGrid = new float[width][height][3];
    }
    
    public void update(Level level) {
        if (width != level.getWidth() || height != level.getHeight())
            throw new IllegalArgumentException("Invalid-sized Level");
        
        TileType.Location loc = new TileType.Location(level);
        for (int x = 0; x < width; x++) {
            loc.x = x;
            for (int y = 0; y < height; y++) {
                loc.y = y;
                long seed = (((long) x) << 32) | (((long) y) << 2);
                loc.rand.setSeed(seed);
                TileType ttype = level.getTile(x, y);
                
                loc.rand.setSeed(seed);
                charGrid[x][y] = ttype.getChar(loc);
                loc.rand.setSeed(seed+1);
                ttype.getFGColor(loc).getRGBColorComponents(fgGrid[x][y]);
                loc.rand.setSeed(seed+2);
                ttype.getBGColor(loc).getRGBColorComponents(bgGrid[x][y]);
            }
        }
    }
    
    public void draw() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                CharDrawing.drawChar(x, y, charGrid[x][y], fgGrid[x][y], bgGrid[x][y]);
            }
        }
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    private int width, height;
    
    private final char[][] charGrid;
    private final float[][][] fgGrid, bgGrid;
    
}