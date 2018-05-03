package projet.pictionnary.breton.common.model;

import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 * This class is used to represent a <code> Point </code> of the draw.
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

    /**
     * Constructs a new <code> Point </code>.
     * 
     * @param x the position on the x axis.
     * @param y the position on the y axis.
     * @param thickness the thickness of the point.
     * @param color the color of the point.
     * @param erase if the point is an erasing point or not.
     * @param linePos the position in the line.
     */
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

    /**
     * Gives the value of x.
     *  
     * @return the value of x.
     */
    public double getX() {
        return x;
    }

    /**
     * Gives the value of y.
     * 
     * @return the value of y.
     */
    public double getY() {
        return y;
    }

    /**
     * Gives the thickness value.
     * 
     * @return the value of thickness.
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * Gives the color of the point.
     * 
     * @return the color of the point.
     */
    public Color getColor() {
        return new Color(colorRed, colorGreen, colorBlue, colorOpacity);
    }

    /**
     * Gives the value of erase.
     * 
     * @return the value of erase.
     */
    public boolean isErase() {
        return erase;
    }
    
    /**
     * Gives the value of linePos.
     * 
     * @return the value of linePos.
     */
    public LinePosition getlinePos() {
        return linePos;
    }
}
