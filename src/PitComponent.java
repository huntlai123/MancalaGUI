import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class PitComponent extends JComponent
{
	private final int DEFAULT_RADIUS = 50;	//	Default radius of pit
	
	private JPanel parentPanel;
	
	private double diameter;					//	Radius of the pit
	private Ellipse2D.Double circ;			//  The pit drawing
	
	/**
	 * Default constructor that sets the default radius to 50
	 */
	public PitComponent()
	{
		parentPanel = null;
		diameter = DEFAULT_RADIUS;
		setSize(parentPanel.getWidth(), parentPanel.getHeight());
	}
	
	/**
	 * Constructor that accepts radius of the pit
	 * @param _rad radius of pit
	 */
	public PitComponent(JPanel myPanel)
	{
		parentPanel = myPanel;
		diameter = (parentPanel.getWidth() * .73)/8;
		setSize(parentPanel.getWidth(), parentPanel.getHeight());
	}
	
	/**
	 * Draws a circle which represents mancala pits
	 * @param c Component drawing this
	 * @param _pen Graphics object
	 * @param x X coordinate of the center of the pit
	 * @param y Y coordinate of the center of the pit
	 */
	public void paintComponent(Graphics _pen)
	{
		super.paintComponent(_pen);
		
		diameter = (parentPanel.getWidth() * .73)/8;
		
		Graphics2D pen = (Graphics2D)_pen;
		
		circ = new Ellipse2D.Double(0, 0, diameter, diameter);
		
		pen.draw(circ);
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(getWidth(), getHeight());
	}
	
	/**
	 * Checks if the give mouse point is within the circle (pit)
	 * @param point The mouse pointer
	 */
	public boolean contains(Point point)
	{
		double x = point.getX();
		double y = point.getY();
		
		if (circ.contains(x, y))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the radius of the pit
	 * @return The radius of the
	 */
	public double getDiameter()
	{	return diameter;	}
}
