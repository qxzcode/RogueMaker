package roguemaker.graphics;

import java.awt.*;
import java.util.Random;

/**
 * @author Quinn Tucker
 */
public class ColorRange {
    
    public ColorRange(float b, float bDev) {
        this(0, 0, 0, 0, b, bDev);
    }
    
    public ColorRange(float h, float hDev,
                      float s, float sDev,
                      float b, float bDev) {
        this.hMin = h - hDev;
        this.hRange = hDev*2;
        this.sMin = s - sDev;
        this.sRange = sDev*2;
        this.bMin = b - bDev;
        this.bRange = bDev*2;
    }
    
    public ColorRange(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hMin = hsb[0];
        sMin = hsb[1];
        bMin = hsb[2];
        hRange = sRange = bRange = 0;
    }
    
    public Color pickRandom() {
        return pickRandom(defaultRand);
    }
    
    public Color pickRandom(Random rand) {
        return Color.getHSBColor(random(rand, hMin, hRange),
                                 random(rand, sMin, sRange),
                                 random(rand, bMin, bRange));
    }
    
    private static float random(Random rand, float min, float range) {
        return rand.nextFloat()*range + min;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ColorRange))
            return false;
        
        ColorRange cr = (ColorRange) obj;
        return cr.hMin == hMin && cr.hRange == hRange &&
               cr.sMin == sMin && cr.sRange == sRange &&
               cr.bMin == bMin && cr.bRange == bRange;
    }
    
    private float hMin, hRange;
    private float sMin, sRange;
    private float bMin, bRange;
    
    private static final Random defaultRand = new Random();
    
}
