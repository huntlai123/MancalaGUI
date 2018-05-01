import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

/**
 * Represents a pit object that will be made into an array. These pit objects will be used for either player's side to hold his/her stones.
 */
public class Pit
{
	private RectangularShape myShape;		// The shape of the pit	
	private int pitNum;						// The pit number of this pit. This value will be used to access corresponding index in circular array in Model
	private boolean isPersonalPit;			// True if this pit is a player's score pit.
	
	/**
	 * Default constructor that creates a basic pit object
	 */
	public Pit()
	{
		myShape = null;
		pitNum = 0;
		isPersonalPit = false;
	}
	
	/**
	 * Constructor specifies the shape type and pit number
	 * @param aShape The shape of this pit
	 * @param aPitNum The pit number of this pit
         * Precondition: RectangularShape object should not be null and the pitNum arg should hold a positive real number
         * Postcondition: a pit object is created that can be used in the Mancala board
	 */
	public Pit(RectangularShape aShape, int aPitNum)
	{
		myShape = aShape;
		pitNum = aPitNum;
		isPersonalPit = false;
	}
	
    /**
    * Checks if the given mouse pointer is within this Pit's shape
    * @param P point location to be checked
    * @return true if the shape contains the point
    */
	public boolean contains(Point P)
	{
		if(myShape.contains(P))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Mutator for shape instance variable
	 * @param aShape RectangularShape instance variable
	 */
	public void setShape(RectangularShape aShape)
	{	myShape = aShape;	}
	
	/**
	 * Accessor for shape instance variable
	 * @return RectangularShape instance variable
	 */
	public RectangularShape getShape()
	{	return myShape;	}
	
	/**
	 * Mutator for pit num value
	 * @param aPitNum int instance variable
	 */
	public void setPitNum(int aPitNum)
	{	pitNum = aPitNum;	}
	
	/**
	 * Accessor for pit num value
	 * @return int instance variable
	 */
	public int getPitNum()
	{	return pitNum;	}
	
	/**
	 * Mutator for isPersonalShape boolean value
	 * @param aType boolean instance variable
	 */
	public void setPersonalShape(boolean aType)
	{	isPersonalPit = aType;	}
	
	/**
	 * Accessor for isPersonalShape boolean value
	 * @return boolean instance variable
	 */
	public boolean isPersonalPit()
	{	return isPersonalPit;	}
}
