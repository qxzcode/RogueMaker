package roguemaker.game;

import roguemaker.RogueMaker;

/**
 * @author Quinn Tucker
 */
public class Level {
    
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new TileType[width][height];
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public TileType getTile(int x, int y) { return tiles[x][y]; }
    public void setTile(int x, int y, int id) { tiles[x][y] = RogueMaker.getTileType(id); }
    
    protected int width, height;
    
    protected TileType[][] tiles;
    
}
