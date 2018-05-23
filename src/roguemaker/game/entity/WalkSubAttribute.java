package roguemaker.game.entity;

import java.util.Optional;

/**
 * @author Quinn Tucker
 */
public class WalkSubAttribute extends MoveAttribute.SubAttribute {
    
    @Override
    public Optional<Boolean> canMoveTo(int x, int y) {
        if (!parent.entity.level.getTile(x, y).isSolid())
            return Optional.of(true);
        else
            return Optional.empty();
    }
    
}
