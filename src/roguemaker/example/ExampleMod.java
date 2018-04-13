package roguemaker.example;

import roguemaker.Mod;
import roguemaker.RogueMaker;
import roguemaker.game.BasicPlayer;
import roguemaker.game.BasicTileType;
import roguemaker.graphics.ColorRange;

import java.awt.*;

/**
 * @author Quinn Tucker
 */
@Mod
public class ExampleMod {
    
    public ExampleMod() {
        // register tile types
        FLOOR_ID = RogueMaker.registerTileType(new BasicTileType(
                '.', Color.LIGHT_GRAY, new ColorRange(0.98f, 0.02f),
                false, false
        ));
        WALL_ID  = RogueMaker.registerTileType(new BasicTileType(
                '#', Color.BLACK,      new ColorRange(0.5f, 0.05f),
                true, true
        ));
        
        // generate the level
        RogueMaker.setLevel(new SimpleGenerator().generate());
        
        // register the player
        PlayerEntity player = new PlayerEntity(RogueMaker.getLevel(), 9, 4);
        RogueMaker.getLevel().addEntity(player);
        RogueMaker.registerPlayer(new BasicPlayer(player));
    }
    
    public static int FLOOR_ID, WALL_ID;
    
}
