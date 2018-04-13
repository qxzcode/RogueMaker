package roguemaker.example;

import roguemaker.game.Level;
import roguemaker.game.LevelGenerator;
import roguemaker.game.PlayerEntity;

import static roguemaker.example.ExampleMod.FLOOR_ID;
import static roguemaker.example.ExampleMod.WALL_ID;

/**
 * @author Quinn Tucker
 */
public class SimpleGenerator implements LevelGenerator {
    
    @Override
    public Level generate() {
        Level level = new Level(50, 30);
        LevelGenerator.generateCaves(level, FLOOR_ID, WALL_ID, 0.5);
        
        level.addEntity(new DerpEntity(level, 7, 6));
        level.addEntity(new PlayerEntity(level, 9, 4));
        return level;
    }
    
}
