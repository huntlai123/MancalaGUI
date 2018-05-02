import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.lang.Math;


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
	 * Draws the style along with the label of each pit
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
		
		pen.drawString("A1", (int)pits[0].getShape().getX() + (int)pits[0].getShape().getWidth()/4, (int)pits[0].getShape().getY() + (int)(pits[0].getShape().getHeight() * 1.5));
		pen.drawString("A2", (int)pits[1].getShape().getX() + (int)pits[1].getShape().getWidth()/4, (int)pits[1].getShape().getY() + (int)(pits[1].getShape().getHeight() * 1.5));
		pen.drawString("A3", (int)pits[2].getShape().getX() + (int)pits[2].getShape().getWidth()/4, (int)pits[2].getShape().getY() + (int)(pits[2].getShape().getHeight() * 1.5));
		pen.drawString("A4", (int)pits[3].getShape().getX() + (int)pits[3].getShape().getWidth()/4, (int)pits[3].getShape().getY() + (int)(pits[3].getShape().getHeight() * 1.5));
		pen.drawString("A5", (int)pits[4].getShape().getX() + (int)pits[4].getShape().getWidth()/4, (int)pits[4].getShape().getY() + (int)(pits[4].getShape().getHeight() * 1.5));
		pen.drawString("A6", (int)pits[5].getShape().getX() + (int)pits[5].getShape().getWidth()/4, (int)pits[5].getShape().getY() + (int)(pits[5].getShape().getHeight() * 1.5));
		
		pen.drawString("Player 1 >>>", getWidth()/2 - getWidth()/10, (int)pits[2].getShape().getY() + (int)(pits[2].getShape().getHeight() * 2));
		
		pen.drawString("B1", (int)pits[7].getShape().getX() + (int)pits[7].getShape().getWidth()/4, (int)pits[7].getShape().getY() - (int)(pits[7].getShape().getHeight()/7));
		pen.drawString("B2", (int)pits[8].getShape().getX() + (int)pits[8].getShape().getWidth()/4, (int)pits[8].getShape().getY() - (int)(pits[8].getShape().getHeight()/7));
		pen.drawString("B3", (int)pits[9].getShape().getX() + (int)pits[9].getShape().getWidth()/4, (int)pits[9].getShape().getY() - (int)(pits[9].getShape().getHeight()/7));
		pen.drawString("B4", (int)pits[10].getShape().getX() + (int)pits[10].getShape().getWidth()/4, (int)pits[10].getShape().getY() - (int)(pits[10].getShape().getHeight()/7));
		pen.drawString("B5", (int)pits[11].getShape().getX() + (int)pits[11].getShape().getWidth()/4, (int)pits[11].getShape().getY() - (int)(pits[11].getShape().getHeight()/7));
		pen.drawString("B6", (int)pits[12].getShape().getX() + (int)pits[12].getShape().getWidth()/4, (int)pits[12].getShape().getY() - (int)(pits[12].getShape().getHeight()/7));
		
		pen.drawString("<<< Player 2", getWidth()/2 - getWidth()/10, (int)pits[10].getShape().getY() - (int)(pits[10].getShape().getY()/1.8));
		
		pen.drawString("MA", (int)pits[6].getShape().getX() - getWidth()/12, (int)pits[6].getShape().getCenterY());
		pen.drawString("MB", (int)pits[13].getShape().getX() + getWidth()/10, (int)pits[13].getShape().getCenterY());
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
    /**
    public String getUndoCount()
    {
    	return "("+model.getUndos()+")";
    }
    **/
}