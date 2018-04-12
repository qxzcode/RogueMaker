package roguemaker.game;

import roguemaker.RogueMaker;
import roguemaker.game.entity.Entity;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Quinn Tucker
 */
public class Level {
    
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new TileType[width][height];
        visibility = new boolean[width][height];
    }
    
    public void update() {
        for (Entity e : entities) {
            e.update();
        }
        entities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();
        
        Entity player = entities.iterator().next();
        new Visibility(this, player.x, player.y).compute();
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public TileType getTile(int x, int y) {
        return tiles[x][y];
    }
    public void setTile(int x, int y, int id) {
        tiles[x][y] = RogueMaker.getTileType(id);
    }
    
    public void addEntity(Entity e) {
        assert e.x >= 0 && e.y >= 0 && e.x < width && e.y < height;
        entities.add(e);
    }
    public void removeEntity(Entity e) {
        entitiesToRemove.add(e);
    }
    
    public void forEachEntity(Consumer<? super Entity> action) {
        entities.forEach(action);
    }
    public void forEachEntityAt(int x, int y, Consumer<? super Entity> action) {
        for (Entity e : entities) {
            if (e.x == x && e.y == y) {
                action.accept(e);
            }
        }
    }
    
    protected int width, height;
    
    protected TileType[][] tiles;
    public boolean[][] visibility;
    protected Set<Entity> entities = new HashSet<>();
    protected List<Entity> entitiesToRemove = new ArrayList<>();
    
}
