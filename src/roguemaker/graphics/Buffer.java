package roguemaker.graphics;

import roguemaker.game.Level;
import roguemaker.game.TileType;

import java.awt.*;
import java.util.function.Function;

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
        
        // tiles
        TileType.Location loc = new TileType.Location(level);
        for (int x = 0; x < width; x++) {
            loc.x = x;
            for (int y = 0; y < height; y++) {
                loc.y = y;
                TileType ttype = level.getTile(x, y);
                long seed = ((((long) x) << 32) | (((long) y) << 2)) ^ ttype.hashCode();
                
                Function<Color, Color> cFunc = level.visibility[x][y]?
                                                   c -> c :
                                                   level.explored[x][y]?
                                                       c -> c.darker().darker().darker().darker() :
                                                       c -> Color.BLACK;
                
                loc.setSeed(seed);
                charGrid[x][y] = ttype.getChar(loc);
                loc.setSeed(seed+1);
                cFunc.apply(ttype.getFGColor(loc)).getRGBColorComponents(fgGrid[x][y]);
                loc.setSeed(seed+2);
                cFunc.apply(ttype.getBGColor(loc)).getRGBColorComponents(bgGrid[x][y]);
            }
        }
        
        // entities
        level.forEachEntity(e -> {
            if (level.visibility[e.x][e.y]) {
                charGrid[e.x][e.y] = e.getChar();
                e.getColor().getRGBColorComponents(fgGrid[e.x][e.y]);
            }
        });
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
