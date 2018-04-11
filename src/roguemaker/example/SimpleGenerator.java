package roguemaker.example;

import roguemaker.game.Level;
import roguemaker.game.LevelGenerator;

import static roguemaker.example.ExampleMod.FLOOR_ID;
import static roguemaker.example.ExampleMod.WALL_ID;

/**
 * @author Quinn Tucker
 */
public class SimpleGenerator implements LevelGenerator {
    
    @Override
    public Level generate() {
        Level level = new Level(50, 30);
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                level.setTile(x, y, (x*x + 3*y) % 5 == 0? WALL_ID : FLOOR_ID);
            }
        }
        return level;
    }
    
}
