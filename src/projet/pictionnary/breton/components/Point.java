package projet.pictionnary.breton.components;

import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class Point implements Serializable {

    private final double x;
    private final double y;
    private final int thickness;
    private final double colorRed;
    private final double colorGreen;
    private final double colorBlue;
    private final double colorOpacity;
    private final boolean erase;
    private final LinePosition linePos;

    public Point(double x, double y, int thickness, Color color, boolean erase,
                 LinePosition linePos) {
        this.x = x;
        this.y = y;
        this.thickness = thickness;
        colorRed = color.getRed();
        colorGreen = color.getGreen();        
        colorBlue = color.getBlue();
        colorOpacity = color.getOpacity();
        this.erase = erase;
        this.linePos = linePos;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getThickness() {
        return thickness;
    }

    public Color getColor() {
        return new Color(colorRed, colorGreen, colorBlue, colorOpacity);
    }

    public boolean isErase() {
        return erase;
    }
    
    public LinePosition getlinePos() {
        return linePos;
    }
}
