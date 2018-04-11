package roguemaker.example;

import roguemaker.Mod;
import roguemaker.RogueMaker;
import roguemaker.game.BasicTileType;
import roguemaker.graphics.ColorRange;

import java.awt.*;

/**
 * @author Quinn Tucker
 */
@Mod
public class ExampleMod {
    
    public ExampleMod() {
        FLOOR_ID = RogueMaker.registerTileType(new BasicTileType(
                '.', Color.LIGHT_GRAY, new ColorRange(0.98f, 0.02f),
                false
        ));
        WALL_ID  = RogueMaker.registerTileType(new BasicTileType(
                '#', Color.BLACK,      new ColorRange(0.5f, 0.05f),
                true
        ));
        RogueMaker.setLevel(new SimpleGenerator().generate());
    }
    
    public static int FLOOR_ID, WALL_ID;
    
}
