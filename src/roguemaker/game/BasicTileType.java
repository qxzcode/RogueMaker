package roguemaker.game;

import roguemaker.graphics.ColorRange;

import java.awt.*;

/**
 * @author Quinn Tucker
 */
public class BasicTileType extends TileType {
    
    public BasicTileType(char c, ColorRange fg, ColorRange bg) {
        character = c;
        fgColor = fg;
        bgColor = bg;
    }
    
    public BasicTileType(char c, Color fg, Color bg) {
        this(c, new ColorRange(fg), new ColorRange(bg));
    }
    
    public BasicTileType(char c, ColorRange fg, Color bg) {
        this(c, fg, new ColorRange(bg));
    }
    
    public BasicTileType(char c, Color fg, ColorRange bg) {
        this(c, new ColorRange(fg), bg);
    }
    
    @Override
    public char getChar(Location loc) {
        return character;
    }
    
    @Override
    public Color getFGColor(Location loc) {
        return fgColor.pickRandom(loc.rand);
    }
    
    @Override
    public Color getBGColor(Location loc) {
        return bgColor.pickRandom(loc.rand);
    }
    
    protected char character;
    protected ColorRange fgColor, bgColor;
    
}
