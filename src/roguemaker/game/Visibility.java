package roguemaker.game;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.IntBinaryOperator;

/**
 * @author Quinn Tucker
 */
public class Visibility {
    
    private static class ColumnPortion {
        
        final int x;
        final int bottomX, bottomY;
        final int topX, topY;
        
        ColumnPortion(int x, int bottomX, int bottomY, int topX, int topY) {
            this.x = x;
            this.bottomX = bottomX;
            this.bottomY = bottomY;
            this.topX = topX;
            this.topY = topY;
        }
        
    }
    
    public interface Action {
        void visible(int x, int y);
    }
    
    public Visibility(Level level, int cx, int cy) {
        this.level = level;
        this.cx = cx;
        this.cy = cy;
    }
    
    /**
     * NOTE: action may be invoked more than once per visible tile
     */
    public void compute(Action action) {
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                level.visibility[x][y] = false;
            }
        }
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
        
        Queue<ColumnPortion> queue = new LinkedList<>();
        queue.add(new ColumnPortion(0, 1, 0, 1, 1));
        while (!queue.isEmpty()) {
            ColumnPortion cur = queue.remove();
            
            // compute ray Y positions in the current column
            final int bottom = getY(cur.x, cur.bottomX, cur.bottomY);
            final int top = getY(cur.x, cur.topX, cur.topY);
            
            // process the range
            Optional<Boolean> wasLastOpaque = Optional.empty();
            int bx = cur.bottomX, by = cur.bottomY;
            int tx = cur.topX, ty = cur.topY;
            for (int y = top; y >= bottom; y--) {
                // mark the cell as visible
                int wx = xFunc.applyAsInt(cur.x, y), wy = yFunc.applyAsInt(cur.x, y);
                action.visible(wx, wy);
                
                boolean opaque = isOpaque(wx, wy);
                if (wasLastOpaque.isPresent()) {
                    if (opaque) {
                        if (!wasLastOpaque.get()) {
                            queue.add(new ColumnPortion(
                                    cur.x + 1,
                                    cur.x*2 - 1, y*2 + 1,
                                    tx, ty
                            ));
                        }
                    } else if (wasLastOpaque.get()) {
                        tx = cur.x*2 + 1;
                        ty = y*2 + 1;
                    }
                }
                wasLastOpaque = Optional.of(opaque);
            }
            if (wasLastOpaque.isPresent() && !wasLastOpaque.get()) {
                queue.add(new ColumnPortion(cur.x + 1, bx, by, tx, ty));
            }
        }
    }
    
    private int getY(int x, int dx, int dy) {
        int num = (2*x - 1) * dy, den = 2*dx;
        int inY  = getYQuot(num, den, dx);
        int outY = getYQuot(num + 2*dy, den, dx);
        return isOpaque(xFunc.applyAsInt(x, inY), yFunc.applyAsInt(x, inY))? inY : outY;
    }
    
    private int getYQuot(int num, int den, int dx) {
        int quotient = num / den, remainder = num % den;
        if (remainder > dx) quotient++;
        return quotient;
    }
    
    private boolean isOpaque(int wx, int wy) {
        return level.getTile(wx, wy).isOpaque();
    }
    
    private final Level level;
    private final int cx, cy;
    private IntBinaryOperator xFunc, yFunc;
    
}
