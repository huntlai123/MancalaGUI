import java.awt.*;
import java.awt.geom.*;

/**
 * Represents a style for the Mancala program that uses circle components for the pit.
 */
public class BoardStyleCircle implements BoardStyle
{
    private Color c;
    
    /**
     * Draw method that will be called to draw the circle pits into the frame. It specifies the size of the circles 
     * that will be drawn and the relative sizing to each other.
     * @param g Graphics2D object that draws the circle components
     * @param width used throughout this style to ensure consistent sizing
     * @param height used throughout this style to ensure consistent sizing
     * @return an array of pits to be drawn
     * Precondition: width and height are positive integer values and g is a non-null Graphics2D object
     * Postcondition: an array of pit objects will be returned with a working draw method to draw the pits.
     */
    public Pit[] createPits(Graphics2D g, int width, int height)
    {
        double diameter = (width * .73)/8;
        double edgeWidth = (width * .27)/9;
        double personalPitLength = height * .7;
        double rowTwoHeight = height * .15; //top
        double rowOneHeight = height - rowTwoHeight - diameter; //bottom
        
        Ellipse2D.Double[] ellipses = new Ellipse2D.Double[14];
        
        //Row 1 and personal pit 1
        
        for(int i = 0; i < 6; i++)
            ellipses[i] = new Ellipse2D.Double((i+5)*edgeWidth + i*diameter, rowOneHeight, diameter, diameter);
        ellipses[6] = new Ellipse2D.Double(edgeWidth, rowTwoHeight,diameter, personalPitLength);
        
        int j = 0;  //used to access the lower row's x value
        
        //Row 2 and personal pit 2
        
        for(int i = 7; i < 13; i++)
        {
            j += 2;
            ellipses[i]= new Ellipse2D.Double((ellipses[i-j].getX()), rowTwoHeight, diameter, diameter);
        }
        ellipses[13] = new Ellipse2D.Double(8*edgeWidth + 7*diameter, rowTwoHeight, diameter, personalPitLength);

        Pit[] pits = new Pit[14];
        
        for(int i = 0; i < pits.length; i++)
            pits[i] = new Pit(ellipses[i], i);
        
        return pits;
    }
        
    /**
     * Gets the border color of the circle pits.
     * @return color of the border
     */
    public Color getBorderColor()
    {
        return Color.LIGHT_GRAY;
    }
    
    /**
     * Gets the color that will fill the circle pits.
     * @return color of the pit
     */
    public Color getFillColor()
    {
        return new Color(153, 79, 0);
    }
}