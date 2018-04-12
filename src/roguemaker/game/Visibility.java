package roguemaker.game;

import java.util.function.IntBinaryOperator;

/**
 * @author Quinn Tucker
 */
public class Visibility {
    
    private static class Slope {
        Slope(int y, int x) {
            this.x = x;
            this.y = y;
        }
        
        boolean greater(int y, int x) { return this.y*x > this.x*y; }
        boolean greaterOrEqual(int y, int x) { return this.y*x >= this.x*y; }
        boolean less(int y, int x) { return this.y*x < this.x*y; }
        //boolean lessOrEqual(int y, int x) { return this.y*x <= this.x*y; }
        
        final int x, y;
    }
    
    public interface Action {
        void visible(int x, int y);
    }
    
    public Visibility(Level level, int cx, int cy) {
        this(level, cx, cy, -1);
    }
    
    public Visibility(Level level, int cx, int cy, int range) {
        this.level = level;
        this.cx = cx;
        this.cy = cy;
        this.rangeLimit = range;
    }
    
    /**
     * ported from:
     * http://www.adammil.net/blog/v125_Roguelike_Vision_Algorithms.html#mine
     */
    public void compute(Action action) {
        computeOctant((x, y) -> cx+x, (x, y) -> cy+y, action);
        computeOctant((x, y) -> cx+y, (x, y) -> cy+x, action);
        computeOctant((x, y) -> cx-y, (x, y) -> cy+x, action);
        computeOctant((x, y) -> cx-x, (x, y) -> cy+y, action);
        computeOctant((x, y) -> cx-x, (x, y) -> cy-y, action);
        computeOctant((x, y) -> cx-y, (x, y) -> cy-x, action);
        computeOctant((x, y) -> cx+y, (x, y) -> cy-x, action);
        computeOctant((x, y) -> cx+x, (x, y) -> cy-y, action);
    }
    
    private void computeOctant(IntBinaryOperator xFunc, IntBinaryOperator yFunc, Action action) {
        this.xFunc = xFunc;
        this.yFunc = yFunc;
        this.action = action;
        visible(0, 0);
        computeOctant(1, new Slope(1, 1), new Slope(0, 1));
    }
    
    /**
     * the main recursive method
     */
    private void computeOctant(int x, Slope top, Slope bottom) {
        for (; rangeLimit < 0 || x <= rangeLimit; x++) {
            // compute the Y coordinates of the top and bottom of the sector. we maintain that top > bottom
            int topY;
            if (top.x == 1) {
                topY = x;
            } else {
                topY = ((x * 2 - 1) * top.y + top.x) / (top.x * 2);
                if (isBlockOpaqueTrans(x, topY)) {
                    if (top.greaterOrEqual(topY * 2 + 1, x * 2) && !isBlockOpaqueTrans(x, topY + 1))
                        topY++;
                } else {
                    int ax = x * 2; // center
                    if (isBlockOpaqueTrans(x + 1, topY + 1)) ax++;
                    if (top.greater(topY * 2 + 1, ax)) topY++;
                }
            }
            
            int bottomY;
            if (bottom.y == 0) {
                bottomY = 0;
            } else {
                bottomY = ((x * 2 - 1) * bottom.y + bottom.x) / (bottom.x * 2);
                if (bottom.greaterOrEqual(bottomY * 2 + 1, x * 2) && isBlockOpaqueTrans(x, bottomY) &&
                    !isBlockOpaqueTrans(x, bottomY + 1)) {
                    bottomY++;
                }
            }
            
            // go through the tiles in the column now that we know which ones could possibly be visible
            int wasOpaque = -1; // 0:false, 1:true, -1:not applicable
            for (int y = topY; y >= bottomY; y--) {
                if (rangeLimit < 0 || x*x + y*y <= rangeLimit*rangeLimit) {
                    boolean isOpaque = isBlockOpaqueTrans(x, y);
                    boolean isVisible = 
                            isOpaque || ((y != topY || top.greater(y * 4 - 1, x * 4 + 1)) && (y != bottomY || bottom.less(y * 4 + 1, x * 4 - 1)));
                    // NOTE: if you want the algorithm to be either fully or mostly symmetrical, replace the line above with the
                    // following line (and uncomment the Slope.lessOrEqual method). the line ensures that a clear tile is visible
                    // only if there's an unobstructed line to its center. if you want it to be fully symmetrical, also remove
                    // the "isOpaque ||" part and see NOTE comments further down
                    // boolean isVisible = isOpaque || ((y != topY || top.greaterOrEqual(y, x)) && (y != bottomY || bottom.lessOrEqual(y, x)));
                    if (isVisible) visible(x, y);
                    
                    // if we found a transition from clear to opaque or vice versa, adjust the top and bottom vectors
                    if (x != rangeLimit) {
                        if (isOpaque) {
                            if (wasOpaque == 0) {
                                int nx = x * 2, ny = y * 2 + 1;
                                // NOTE: if you're using full symmetry and want more expansive walls (recommended), comment out the next line
                                if (isBlockOpaqueTrans(x, y + 1)) nx--;
                                if (top.greater(ny, nx)) {
                                    if (y == bottomY) {
                                        bottom = new Slope(ny, nx); break;
                                    } else
                                        computeOctant(x + 1, top, new Slope(ny, nx));
                                } else {
                                    if (y == bottomY) return;
                                }
                            }
                            wasOpaque = 1;
                        } else {
                            if (wasOpaque > 0) {
                                int nx = x * 2, ny = y * 2 + 1;
                                // NOTE: if you're using full symmetry and want more expansive walls (recommended), comment out the next line
                                if (isBlockOpaqueTrans(x + 1, y + 1)) nx++;
                                if (bottom.greaterOrEqual(ny, nx)) return;
                                top = new Slope(ny, nx);
                            }
                            wasOpaque = 0;
                        }
                    }
                }
            }
        
            if (wasOpaque != 0) break;
        }
    }
    
    private boolean isBlockOpaqueTrans(int x, int y) {
        return isBlockOpaque(xFunc.applyAsInt(x, y), yFunc.applyAsInt(x, y));
    }
    private boolean isBlockOpaque(int wx, int wy) {
        return outOfBounds(wx, wy) || level.getTile(wx, wy).isOpaque();
    }
    
    private void visible(int x, int y) {
        int wx = xFunc.applyAsInt(x, y), wy = yFunc.applyAsInt(x, y);
        if (!outOfBounds(wx, wy)) {
            action.visible(wx, wy);
        }
    }
    
    private boolean outOfBounds(int wx, int wy) {
        return wx < 0 || wy < 0 || wx >= level.getWidth() || wy >= level.getHeight();
    }
    
    private final Level level;
    private final int cx, cy;
    private final int rangeLimit;
    private IntBinaryOperator xFunc, yFunc;
    private Action action;
    
}
