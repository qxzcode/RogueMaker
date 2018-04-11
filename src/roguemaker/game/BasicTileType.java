package roguemaker.game;

import roguemaker.graphics.ColorRange;

import java.awt.*;

/**
 * @author Quinn Tucker
 */
public class BasicTileType extends TileType {
    
    public BasicTileType(char c, ColorRange fg, ColorRange bg, boolean solid) {
        character = c;
        fgColor = fg;
        bgColor = bg;
        this.solid = solid;
    }
    
    public BasicTileType(char c, Color fg, Color bg, boolean solid) {
        this(c, new ColorRange(fg), new ColorRange(bg), solid);
    }
    
    public BasicTileType(char c, ColorRange fg, Color bg, boolean solid) {
        this(c, fg, new ColorRange(bg), solid);
    }
    
    public BasicTileType(char c, Color fg, ColorRange bg, boolean solid) {
        this(c, new ColorRange(fg), bg, solid);
    }
    
    @Override
    public boolean isSolid() {
        return solid;
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
    
    protected boolean solid;
    
    protected char character;
    protected ColorRange fgColor, bgColor;
    
}
