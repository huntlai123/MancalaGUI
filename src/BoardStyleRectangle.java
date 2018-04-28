import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;

public class BoardStyleRectangle implements BoardStyle
{
    public Pit[] draw(Graphics2D g, int width, int height)
    {
        double diameter = (width * .73)/8;
        double edgeWidth = (width * .27)/9;
        double personalPitLength = height * .7;
        double rowOneHeight = height * .15;
        
        RoundRectangle2D.Double[] rectangles = new RoundRectangle2D.Double[14];

        
        //Row 1 and personal pit 1
        rectangles[0] = new RoundRectangle2D.Double(edgeWidth,
                rowOneHeight,diameter, personalPitLength, diameter/10, diameter/10);
        for(int i = 1; i < 7; i++)
            rectangles[i] = new RoundRectangle2D.Double((i+1)*edgeWidth + i*diameter,
                    rowOneHeight, diameter, diameter, diameter/10, diameter/10);
        /*
        RoundRectangle2D.Double pit1 = new RoundRectangle2D.Double(2*edgeWidth + diameter,
                rowOneHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit2 = new RoundRectangle2D.Double(3*edgeWidth + 2*diameter, rowOneHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit3 = new RoundRectangle2D.Double(4*edgeWidth + 3*diameter, rowOneHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit4 = new RoundRectangle2D.Double(5*edgeWidth + 4*diameter, rowOneHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit5 = new RoundRectangle2D.Double(6*edgeWidth + 5*diameter, rowOneHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit6 = new RoundRectangle2D.Double(7*edgeWidth + 6*diameter, rowOneHeight, diameter, diameter, diameter/10, diameter/10);
       */
        double rowTwoHeight = height - rowOneHeight - diameter;
        
        //Row 2 and personal pit 2
        rectangles[7] = new RoundRectangle2D.Double(8*edgeWidth + 7*diameter, rowOneHeight, diameter, personalPitLength, diameter/10, diameter/10);
        for(int i = 1; i < 7; i++)
            rectangles[i+7] = new RoundRectangle2D.Double((i+1)*edgeWidth + i*diameter, rowTwoHeight, diameter, diameter, diameter/10, diameter/10);
        /*
        RoundRectangle2D.Double pit8 = new RoundRectangle2D.Double(3*edgeWidth + 2*diameter, rowTwoHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit9 = new RoundRectangle2D.Double(4*edgeWidth + 3*diameter, rowTwoHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit10 = new RoundRectangle2D.Double(5*edgeWidth + 4*diameter, rowTwoHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit11 = new RoundRectangle2D.Double(6*edgeWidth + 5*diameter, rowTwoHeight, diameter, diameter, diameter/10, diameter/10);
        RoundRectangle2D.Double pit12 = new RoundRectangle2D.Double(7*edgeWidth + 6*diameter, rowTwoHeight, diameter, diameter, diameter/10, diameter/10);
        */
        
        Pit[] pits = new Pit[14];
        
        for(int i = 0; i < pits.length; i++)
            pits[i] = new Pit(rectangles[i], i);
        
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