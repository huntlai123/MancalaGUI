import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Style interface that sets requirements for the rest of the styles that will 
 * implement it.
 * @author Fantastic Four - Hunter Lai, Alejandro Lopez, Dale Christian Seen
 */
public interface BoardStyle
{
    /**
     * Draw method to be overriden by implementing class. 
     * @param g Graphics2D object that will draw the individual components
     * @param width used to determine the size of the components along with spacing within
     * @param height used to determine the size of the components along with spacing within
     * @return Pit array that will be drawn to the panel or frame
     */
    public Pit[] createPits(Graphics2D g, int width, int height);
    
    /**
     * Get the fill color of the pits
     * @return Color of pits
     */
    public Color getFillColor();
    
    /**
     * Get the border color of the pits
     * @return Color of pits
     */
    public Color getBorderColor();
}

