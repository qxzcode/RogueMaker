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
        explored = new boolean[width][height];
    }
    
    public void update() {
        for (Entity e : entities) {
            e.update();
        }
        entities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();
        
        computeVisibility();
    }
    
    public void computeVisibility() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                visibility[x][y] = false;
            }
        }
        Entity player = RogueMaker.getPlayer().getEntity();
        new Visibility(this, player.x, player.y).compute((x, y) -> {
            visibility[x][y] = true;
            explored[x][y] = true;
        });
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public TileType getTile(int x, int y) {
        return tiles[x][y];
    }
    public void setTile(int x, int y, int id) {
        tiles[x][y] = RogueMaker.getTileType(id);
    }
    
    public Entity addEntity(Entity e) {
        if (e.x < 1 || e.y < 1 || e.x >= width-1 || e.y >= height-1)
            throw new IllegalArgumentException("Tried to add an Entity out of bounds");
        e.level = this;
        entities.add(e);
        return e;
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
    public final boolean[][] visibility, explored;
    protected Set<Entity> entities = new HashSet<>();
    protected List<Entity> entitiesToRemove = new ArrayList<>();
    
}
