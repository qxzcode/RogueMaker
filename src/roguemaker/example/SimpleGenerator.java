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
        LevelGenerator.generateCaves(level, FLOOR_ID, WALL_ID, 0.45);
        
        level.addEntity(ExampleMod.derpFactory.create(26, 16));
        return level;
    }
    
}
