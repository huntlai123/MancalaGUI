import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Board Panel
 * @author Fantastic Four
 *
 */
public class BoardPanel extends JPanel implements ChangeListener
{
	private final int DEFAULT_WIDTH = 700;				//	Default width of the panel
	private final int DEFAULT_HEIGHT = 500;				//	Default height of the panel
	private final int NUM_OF_PITS = 14;					//  Default number of pits.
	
	private BoardStyle style;							//	Reference to BoardStyle object
	private Board model;								// 	Reference to Board(Model)
	
	private Pit[] pits;									//  Array of Pits
	
	/**
	 *	Default constructor. Sets style to null and dimensions to their default values.
	 */
	public BoardPanel()
	{
		style = null;
		model = null;
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
	public BoardPanel(Board model, BoardStyle _style)
	{
		style = _style;
		this.model = model;
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
	public BoardPanel(Board model, BoardStyle _style, int width, int height)
	{
		style = _style;
		this.model = model;
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
		pits = style.createPits(pen, getWidth(), getHeight());
		CircularList<Integer> pitValues = model.getCircularList();
		for(int i = 0; i < pitValues.size(); i++)
		{
		    pen.setColor(style.getFillColor());
		    pen.fill(pits[i].getShape());
		    pen.setColor(style.getBorderColor());
		    pen.draw(pits[i].getShape());
		    pen.setFont(new Font("TimesRoman", Font.BOLD, 24));  
		    pen.drawString(Integer.toString(pitValues.get(i)), 
		            (int) pits[i].getShape().getCenterX(), (int) pits[i].getShape().getCenterY());		    
		}
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
        
    /**
    * Attaches a mouse action listener to the controller
    */
    private void initializeMouseListener()
	{
		addMouseListener(new MouseAdapter()
                        {
                                public void mouseClicked(MouseEvent mEvent) 
                                {
                                        Point MousePoint = mEvent.getPoint();
                                        for (int i = 0; i < pits.length; i++)
                                        {
                                                if (pits[i].contains(MousePoint) && model.allowMove(pits[i].getPitNum()))
                                                {                                                    
                                                        model.updateBoard(pits[i].getPitNum());                                                        
                                                }
                                        }
                                        if (model.endGame())
                                        {
                                        	endScreen();
                                        }
                                }					
                        });
	}
	
    /**
    * Repaints the board if the state has changed
    * @param e event to have happened
    */
	public void stateChanged(ChangeEvent e)
	{
	    repaint();
	}
    
	/**
	 * Creates a frame notifying the player that the game has ended while displaying the winner.
	 */
    private void endScreen()
    {
    	JFrame frame = new JFrame();
        frame.setBounds(this.getWidth(), this.getHeight(), 200, 200);
        frame.setTitle("Game Over");
        frame.add(new JTextField(model.getWinningPlayer()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    /**
     * Calls the Board(Model)'s undo method.
     */
    public void undoLast()
    {
    	model.undo();
    }
    
    /**
     * Returns the number of undos left in String form
     * @return String containing the number of undos left.
     */
    public String getUndoCount()
    {
    	return "("+model.getUndos()+")";
    }
}