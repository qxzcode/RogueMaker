package roguemaker;

import roguemaker.game.Level;
import roguemaker.game.Player;
import roguemaker.game.TileType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Quinn Tucker
 */
public class RogueMaker {
    
    private RogueMaker() {} // not allowed to be instantiated
    
    public static int registerTileType(TileType type) {
        tileTypes.add(Objects.requireNonNull(type));
        return tileTypes.size() - 1;
    }
    
    public static TileType getTileType(int id) {
        return tileTypes.get(id);
    }
    
    public static void registerPlayer(Player player) {
        if (RogueMaker.player != null) {
            throw new IllegalStateException("Only one Player may be registered");
        }
        RogueMaker.player = Objects.requireNonNull(player);
    }
    
    public static Player getPlayer() {
        return player;
    }
    
    public static Level getLevel() {
        return level;
    }
    
    public static void setLevel(Level level) {
        RogueMaker.level = Objects.requireNonNull(level);
    }
    
    public static boolean isKeyPressed(int key) {
        return Main.pressedKeys.contains(key);
    }
    
    private static ArrayList<TileType> tileTypes = new ArrayList<>();
    private static Level level;
    private static Player player;
    
}
