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
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                boolean isBorder = x==0 || y==0 || x==level.getWidth()-1 || y==level.getHeight()-1;
                level.setTile(x, y, isBorder || (x*x + 3*y) % 5 == 1? WALL_ID : FLOOR_ID);
            }
        }
        level.addEntity(new DerpEntity(level, 7, 6));
        level.addEntity(new PlayerEntity(level, 9, 4));
        return level;
    }
    
}
