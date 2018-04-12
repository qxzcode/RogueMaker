package roguemaker.game;

import roguemaker.graphics.ColorRange;

import java.awt.*;

/**
 * @author Quinn Tucker
 */
public class BasicTileType extends TileType {
    
    public BasicTileType(char c, ColorRange fg, ColorRange bg, boolean solid, boolean opaque) {
        character = c;
        fgColor = fg;
        bgColor = bg;
        this.solid = solid;
        this.opaque = opaque;
    }
    
    public BasicTileType(char c, Color fg, Color bg, boolean solid, boolean opaque) {
        this(c, new ColorRange(fg), new ColorRange(bg), solid, opaque);
    }
    
    public BasicTileType(char c, ColorRange fg, Color bg, boolean solid, boolean opaque) {
        this(c, fg, new ColorRange(bg), solid, opaque);
    }
    
    public BasicTileType(char c, Color fg, ColorRange bg, boolean solid, boolean opaque) {
        this(c, new ColorRange(fg), bg, solid, opaque);
    }
    
    @Override
    public boolean isSolid() {
        return solid;
    }
    
    @Override
    public boolean isOpaque() {
        return opaque;
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
    
    protected boolean solid, opaque;
    
    protected char character;
    protected ColorRange fgColor, bgColor;
    
}
