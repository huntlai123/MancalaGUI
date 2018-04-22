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
	
	private BoardStyle style;							//	Reference to BoardStyle object
	private JLabel pitsLabel;
	
	/**
	 *	Default constructor. Sets style to null and dimensions to their default values.
	 */
	BoardPanel()
	{
		style = null;
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setLayout(new SpringLayout());
		addComponents();
	}
	
	/**
	 * Constructor that has a BoardStyle parameter. Initializes style to parameter and the dimensions to their default values
	 * 
	 * @param _style BoardStyle object that will be referenced by instance variable.
	 */
	BoardPanel(BoardStyle _style)
	{
		style = _style;
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setLayout(new SpringLayout());
		addComponents();
	}
	
	/**
	 * Constructor that has a Boardstyle parameter and two integers for dimensions. Initializes style to parameter and the dimension to the two given ints.
	 * @param _style BoardStyle object that will be referenced by BoardPanel
	 * @param _width The width of this BoardPanel
	 * @param _height The height of this BoardPanel
	 */
	BoardPanel(BoardStyle _style, int width, int height)
	{
		style = _style;
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setLayout(new SpringLayout());
		addComponents();
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
	
	private void addComponents()
	{
		PitComponent[] pitArr = new PitComponent[12];
		for (int i = 0; i < 12; i++)
		{
			pitArr[i] = new PitComponent(this);
			
			PitComponent curPit = pitArr[i];
			
			add(curPit);
			
			curPit.addMouseListener(new MouseListener()
			{
				public void mouseClicked(MouseEvent arg0)
				{
					// Notify corresponding pit in Model
					Point mousePoint = arg0.getPoint();
					if (curPit.contains(mousePoint))
					{
						System.out.println("test");
					}
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {}
			});
			
			SpringLayout layout = (SpringLayout)getLayout();
			if (i == 0)
			{
				layout.putConstraint(SpringLayout.WEST, pitArr[i], 2*(int)((getWidth() * .27)/9) + (int)pitArr[i].getDiameter(), SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.SOUTH, pitArr[i], (int)(getHeight() * 0.15), SpringLayout.SOUTH, this);
			}
			else if (i > 0 && i <= 5)
			{
				layout.putConstraint(SpringLayout.WEST, pitArr[i], (int)((getWidth() * .27)/9) + (int)pitArr[i].getDiameter(), SpringLayout.WEST, pitArr[i-1]);
				layout.putConstraint(SpringLayout.SOUTH, pitArr[i], (int)(getHeight() * 0.15), SpringLayout.SOUTH, this);
			}
			else if (i == 6)
			{
				layout.putConstraint(SpringLayout.WEST, pitArr[i], 2*(int)((getWidth() * .27)/9) + (int)pitArr[i].getDiameter(), SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.SOUTH, pitArr[i], (int)(getHeight() - (getHeight() * 0.15) - pitArr[i].getDiameter()), SpringLayout.SOUTH, this);
			}
			else
			{
				layout.putConstraint(SpringLayout.WEST, pitArr[i], (int)((getWidth() * .27)/9) + (int)pitArr[i].getDiameter(), SpringLayout.WEST, pitArr[i-1]);
				layout.putConstraint(SpringLayout.SOUTH, pitArr[i], (int)(getHeight() - (getHeight() * 0.15) - pitArr[i].getDiameter()), SpringLayout.SOUTH, this);
			}
		}
	}
	
	public void paintComponent(Graphics _pen)
	{
		super.paintComponent(_pen);
		Graphics2D pen = (Graphics2D)_pen;
		style.draw(pen, getWidth(), getHeight());
	}
}