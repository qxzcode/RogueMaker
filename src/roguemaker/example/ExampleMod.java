package roguemaker.example;

import roguemaker.Mod;
import roguemaker.RogueMaker;
import roguemaker.game.BasicPlayer;
import roguemaker.game.BasicTileType;
import roguemaker.game.entity.Entity;
import roguemaker.game.entity.VisualAttribute;
import roguemaker.graphics.ColorRange;

import java.awt.Color;

/**
 * @author Quinn Tucker
 */
@Mod
public class ExampleMod {
    
    public ExampleMod() {
        // register tile types
        FLOOR_ID = RogueMaker.registerTileType(new BasicTileType(
                (char)249,
                new ColorRange(0.65f, 0.04f, 0.7f, 0.05f, 0.70f, 0.03f),
                //new ColorRange(0.35f, 0.07f, 0.7f, 0.05f, 0.90f, 0.1f),
                new ColorRange(0.65f, 0.04f, 0.7f, 0.05f, 0.15f, 0.03f),
                false, false
        ));
        WALL_ID  = RogueMaker.registerTileType(new BasicTileType(
                '#', new ColorRange(0.5f, 0.05f), new ColorRange(0.9f, 0.05f),
                true, true
        ));
        
        // generate the level
        RogueMaker.setLevel(new SimpleGenerator().generate());
        
        // register the player
        Entity player = RogueMaker.getLevel().addEntity(new Entity(
                25, 15,
                new VisualAttribute('@', Color.ORANGE.darker())
        ));
        RogueMaker.registerPlayer(new BasicPlayer(player));
    }
    
    public static int FLOOR_ID, WALL_ID;
    
}
