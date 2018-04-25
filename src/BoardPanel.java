import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Board Panel
 * @author DaleS
 *
 */
public class BoardPanel extends JPanel
{
	private final int DEFAULT_WIDTH = 700;				//	Default width of the panel
	private final int DEFAULT_HEIGHT = 500;				//	Default height of the panel
	private final int NUM_OF_PITS = 14;
	
	private BoardStyle style;							//	Reference to BoardStyle object
	private Board model;								// 	Reference to Board(Model)
	
	private Pit[] pits;									//  Array of Pits
	
	/**
	 *	Default constructor. Sets style to null and dimensions to their default values.
	 */
	public BoardPanel()
	{
		style = null;
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		pits = new Pit[NUM_OF_PITS];
		initializeMouseListener();
	}
	
	/**
	 * Constructor that has a BoardStyle parameter. Initializes style to parameter and the dimensions to their default values
	 * 
	 * @param _style BoardStyle object that will be referenced by instance variable.
	 */
	public BoardPanel(BoardStyle _style)
	{
		style = _style;
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		pits = new Pit[NUM_OF_PITS];
		initializeMouseListener();
	}
	
	/**
	 * Constructor that has a Boardstyle parameter and two integers for dimensions. Initializes style to parameter and the dimension to the two given ints.
	 * @param _style BoardStyle object that will be referenced by BoardPanel
	 * @param _width The width of this BoardPanel
	 * @param _height The height of this BoardPanel
	 */
	public BoardPanel(BoardStyle _style, int width, int height)
	{
		style = _style;
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		pits = new Pit[NUM_OF_PITS];
		initializeMouseListener();
	}
	
	/**
	 * Draws the style
	 */
	public void paintComponent(Graphics _pen)
	{
		super.paintComponent(_pen);
		Graphics2D pen = (Graphics2D)_pen;
		style.draw(pen, getWidth(), getHeight());
	}
	
	/**
	 * Mutator for BoardStyle instance variable
	 * @param _style BoardStyle object that will be referenced by instance variable.
	 */
	public void setBoardStyle(BoardStyle _style)
	{	style = _style;	}
	
	/**
	 * Accessor for BoardStyle instance variable
	 * @return
	 */
	public BoardStyle getBoardStyle()
	{	return style;	}
	
	private void initializeMouseListener()
	{
		addMouseListener(new MouseListener()
				{
					public void mouseClicked(MouseEvent mEvent) 
					{
						Point MousePoint = mEvent.getPoint();
						for (int i = 0; i < pits.length; i++)
						{
							if (pits[i].contains(MousePoint))
							{
								model.updateBoard(pits[i].getPitNum());
							}
						}
					}
					public void mouseEntered(MouseEvent arg0) {}
					public void mouseExited(MouseEvent arg0) {}
					public void mousePressed(MouseEvent arg0) {}
					public void mouseReleased(MouseEvent arg0) {}
				});
	}
}