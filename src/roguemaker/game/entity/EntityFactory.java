package roguemaker.game.entity;

/**
 * @author Quinn Tucker
 */
public interface EntityFactory {
    
    Entity create(int x, int y);
    
}
