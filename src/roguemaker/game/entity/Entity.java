package roguemaker.game.entity;

import roguemaker.game.Level;

import java.awt.Color;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author Quinn Tucker
 */
public class Entity {
    
    public Entity(int x, int y, Attribute... attrs) {
        this.x = x;
        this.y = y;
        attributes = new ArrayList<>(Arrays.asList(attrs));
        for (Attribute a : attributes) {
            a.entity = this;
        }
    }
    
    public void update() {
        for (Attribute a : attributes) {
            a.update();
        }
    }
    
    public char getChar() {
        return Attribute.getProperty(attributes, Attribute::getChar);
    }
    
    public Color getColor() {
        return Attribute.getProperty(attributes, Attribute::getColor);
    }
    
    @SuppressWarnings("unchecked")
    public <A extends Attribute> A getAttribute(Class<A> type) {
        for (Attribute a : attributes) {
            if (type.isAssignableFrom(a.getClass())) {
                return (A) a;
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public <A extends Attribute> void forEachAttribute(Class<A> type, Consumer<A> action) {
        for (Attribute a : attributes) {
            if (type.isAssignableFrom(a.getClass())) {
                action.accept((A) a);
            }
        }
    }
    
    public int x, y;
    public Level level;
    public final ArrayList<Attribute> attributes;
    
}
