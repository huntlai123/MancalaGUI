import java.awt.*;
import java.awt.geom.*;

public class BoardStyleCircle implements BoardStyle
{
    private Color c;
    
    public Pit[] draw(Graphics2D g, int width, int height)
    {
        double diameter = (width * .73)/8;
        double edgeWidth = (width * .27)/9;
        double personalPitLength = height * .7;
        double rowOneHeight = height * .15;
        
        Ellipse2D.Double[] ellipses = new Ellipse2D.Double[14];
        
        //Row 1 and personal pit 1
        ellipses[0] = new Ellipse2D.Double(edgeWidth, rowOneHeight,diameter, personalPitLength);
        for(int i = 1; i < 7; i++)
            ellipses[i] = new Ellipse2D.Double(((i+1)*edgeWidth) + (i*diameter), rowOneHeight, diameter, diameter);
       
        double rowTwoHeight = height - rowOneHeight - diameter;
        
        //Row 2 and personal pit 2
        ellipses[7] = new Ellipse2D.Double(8*edgeWidth + 7*diameter, rowOneHeight, diameter, personalPitLength);
        for(int i = 1; i < 7; i++)
            ellipses[i + 7]= new Ellipse2D.Double((i+1)*edgeWidth + (i*diameter), rowTwoHeight, diameter, diameter);

        Pit[] pits = new Pit[14];
        
        for(int i = 0; i < pits.length; i++)
            pits[i] = new Pit(ellipses[i], i);
        
        return pits;
    }
        
    public Color getBorderColor()
    {
        return Color.LIGHT_GRAY;
    }
    
    public Color getFillColor()
    {
        return new Color(153, 79, 0);
    }
}