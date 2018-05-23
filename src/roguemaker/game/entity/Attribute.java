package roguemaker.game.entity;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Quinn Tucker
 */
public abstract class Attribute {
    
    public void update() {}
    
    public Optional<Character> getChar() {
        return Optional.empty();
    }
    
    public Optional<Color> getColor() {
        return Optional.empty();
    }
    
    protected Entity entity;
    
    
    public static <A, P> P getProperty(Iterable<A> attrs, Function<A, Optional<P>> prop) {
        return getProperty(attrs, prop, null);
    }
    
    public static <A, P> P getProperty(Iterable<A> attrs, Function<A, Optional<P>> prop, P def) {
        Optional<P> c = Optional.ofNullable(def);
        for (A a : attrs) {
            Optional<P> cur = prop.apply(a);
            if (cur.isPresent()) c = cur;
        }
        if (!c.isPresent())
            throw new java.lang.IllegalStateException("Property missing");
        return c.get();
    }
    
}
