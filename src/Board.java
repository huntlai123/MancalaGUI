import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 */
public class Board {
    private final int MAX_UNDOS = 3; 					// maximum number of undos that a player can do
    
    private boolean playerTurn; 						// false = player 1's turn, true = player 2's turn
    private boolean justUndid; 							// True if a player had just undid
    private int undoCounter1;   						// counter for player A
    private int undoCounter2;   						// counter for player B
    private int currHole;   							// current hole to receive stones
    
    private CircularList<Integer> holes;    			// ArrList used to represent the holes in the board including each player's Mancala
    private CircularList<Integer> prevHoles; 
    private ArrayList<ChangeListener> listeners;
    
    /**
     * Default constructor. Builds an empty board. 
     */
    public Board()
    {
       playerTurn = false;
       justUndid = true;
        
       undoCounter1 = MAX_UNDOS;
       undoCounter2 = MAX_UNDOS;
       currHole = 0;
        
       listeners = new ArrayList<ChangeListener>();
        
       holes = new CircularList<Integer>();   //in order to make this circular, need to extend arraylist or create our own circular array
        
       for (int i = 0; i < 14; i++)
       {
           if(i == 6 || i == 13)
               holes.add(0);
           else
               holes.add(0);
       }
        
       prevHoles = null;
    }
    
    /**
     * Constructor that builds the board with the given number of stones
     * @param stoneNum number of stones to be initialized with the board
     */
    public Board(int stoneNum)
    {
    	playerTurn = false;
        justUndid = true;
         
        undoCounter1 = MAX_UNDOS;
        undoCounter2 = MAX_UNDOS;
        currHole = 0;
         
        listeners = new ArrayList<ChangeListener>();
         
        holes = new CircularList<Integer>();   //in order to make this circular, need to extend arraylist or create our own circular array
        
        for (int i = 0; i < 14; i++)
        {
            if(i == 6 || i == 13)
                holes.add(0);
            else
                holes.add(stoneNum);
        }
        
        prevHoles = null;
    }
    
    /**
     * Reverses the last play made.
     */
    public void undo()
    {
    	if (!justUndid)
    	{
    		boolean lastTurnFree = takeAnotherTurn();
    		
    		int currentPlayerUndoCount;
    		
    		if (lastTurnFree == true)
    		{
    			if (playerTurn == false)
    			{
    				currentPlayerUndoCount = undoCounter1;
    			}
    			else
    			{
    				currentPlayerUndoCount = undoCounter2;
    			}
    		}
    		else
    		{
    			if (playerTurn == true)
    			{
    				currentPlayerUndoCount = undoCounter1;
    			}
    			else
    			{
    				currentPlayerUndoCount = undoCounter2;
    			}
    		}
        	
        	if (currentPlayerUndoCount > 0)
        	{
        		holes = prevHoles;
        		prevHoles = null;
        		
        		justUndid = true;
        		
        		if (lastTurnFree == true)
        		{
        			if (playerTurn == false)
        			{
        				undoCounter1--;
        			}
        			else
        			{
        				undoCounter2--;
        			}
        		}
        		else
        		{
        			if (playerTurn == false)
        			{
        				undoCounter2--;
        			}
        			else
        			{
        				undoCounter1--;
        			}
        			changePlayerTurn();
        		}
        		notifyListeners();
        	}
    	}
    }
    
    /**
     * Returns the number of undoes left for player A.
     * @return the number of undoes left for player A.
     */
    public int getUndoCounter1()
    {	return undoCounter1;	}
    
    /**
     * Returns the number of undoes left for player B.
     * @return the number of undoes left for player B.
     */
    public int getUndoCounter2()
    {	return undoCounter2;	}
    
    /**
     * Takes the number of stones in the given hole and distributes them to every
     * hole after it. Ignores the opposing player's Mancala
     * @param holeNum the number of the hole to take stones from
     */
    public void updateBoard(int holeNum)  //might need to add the initial hole as a param
    {
        // logic: place stone in each hole starting from the hole after the chosen hole
        // make sure to ignore the opposing player's Mancala   
    	
    	prevHoles = new CircularList<Integer>();
    	prevHoles.addAll(holes);
    	
    	boolean lastTurnFree;
    	
        currHole = holeNum; //gets the hole to add a stone into
        int numStones = holes.get(currHole);    //take stones from this hole
        holes.set(currHole, 0); //set hole to have 0 stones
        while(numStones > 0)
        {
            currHole = (currHole + 1) % 14;
            if (skipMancala())
                continue;
            incArrVal(currHole);
            numStones--;
            justUndid = false;
        }
        
        lastTurnFree = takeAnotherTurn();
        
        if (lastTurnFree == false)
        {
        	changePlayerTurn();
        }
        
        if (playerTurn != false && currHole >= 0 && currHole <= 5 || playerTurn != true && currHole >= 7 && currHole <= 12)
        {
        	captureStones();
        }
        
        if (lastTurnFree == true)
        {
        	if (playerTurn == true)
            {
            	undoCounter1 = MAX_UNDOS;
            }
            else
            {
            	undoCounter2 = MAX_UNDOS;
            }
        }
        else
        {
        	if (playerTurn == false)
            {
            	undoCounter1 = MAX_UNDOS;
            }
            else
            {
            	undoCounter2 = MAX_UNDOS;
            }
        }
        
        notifyListeners();
    }
    
    /**
     * Checks if the current move is allowed. It checks if the pit that is clicked is on the 
     * pertaining player's side. 
     * @param pitNum the number of the pit that is clicked
     * @return false if a player doesn't click on one of their pits
     */
    public boolean allowMove(int pitNum)
    {
        if(pitNum >= 0 && pitNum <=5 && playerTurn == false)
        {
            //resetUndo = true;
            // undoCounter1 = 0;
            return true;
        }
        else if (pitNum >= 7 && pitNum <= 12 && playerTurn == true)
        {
            // undoCounter2 = 0;
            //resetUndo = false;
            return true;
        }
        else
            return false;
    }
    
    /**
     * Checks if the Mancala belongs to the current player or the opposing player. If it belongs to the 
     * current player, don't skip, otherwise skip over that Mancala
     * @return true if the current Mancala should be skipped
     */
    private boolean skipMancala()
    {
        if (currHole % 14 == 6 && playerTurn == true)
            return true;
        else if (currHole % 14 == 13 && playerTurn == false)
            return true;
        else
            return false;
    }
    
    /**
     * Checks if the current player's can take another turn. Player must place final stone into their 
     * own Mancala or they must click on an empty pit to make this happen. 
     * @return true if the last stone was placed in the current player's Mancala or if they clicked on an empty Mancala
     */
    private boolean takeAnotherTurn()
    {
        if ((currHole % 14 == 6 && playerTurn == false))
        {
            return true;
        }    
        else if ((currHole % 14 == 13 && playerTurn == true))
        {
            return true;
        }
        else if (holes.get(currHole) == 0)
            return true;
            
        else
            return false;
    }
    
    /**
     * Increments the value at the given hole by 1. Updates ChangeListener.
     * @param i location of hole to be changed
     */
    private void incArrVal(int i)
    {
        Integer value = holes.get(i);
        value++;
        holes.set(i, value);
        
        holes.set(i, value);    //redundant
    }
    
    /**
     * Changes to the next player turn
     */
    private void changePlayerTurn()
    {	playerTurn = !playerTurn;	}
    
    /**
     * Gets the current player's turn. 
     * @return false for player 1, true for player 2
     */
    public boolean getPlayerTurn()
    {	return playerTurn;	}
    
    /**
     * Gets the circular ArrayList for the holes
     * @return the pit ArrayList
     */
    public CircularList<Integer> getCircularList()
    {	return holes;	}
    
    /**
     * Attaches the BoardPanel change listener to the model
     * @param l BoardPanel change listener to be attached
     */
    public void attach(ChangeListener l)
    {
        listeners.add(l);
    }
    
    /**
     * Checks if a player has no more stones on their side, if so the opposing player's pits are all
     * added to their Mancala.
     * @return true if either player has an empty side
     */
    public boolean endGame()
    {
        int counter1 = 0;   //counts player 1's side
        int counter2 = 0;   //counts player 2's side
        int sum = 0;        //sums stones to be added
        for (int i = 0; i < 6; i++)     //checks if player 1's side is empty
        {
            if (holes.get(i) == 0)
            {
                counter1++;
            }
        }
        for (int i = 7; i < 13; i++)    //checks if player 2's side is empty
        {
            if (holes.get(i) == 0)
            {
                counter2++;
            }
        }
        
        if(counter1 == 6)               //if player 1's side is empty, sum all stones in p2's side
        {
            for (int i = 7; i < 13; i++)
            {
                sum += holes.get(i);
                holes.set(i, 0);
            }
            sum += holes.get(13);
            holes.set(13, sum); //set hole to have the sum of all the stones on this player's side
            return true;
        }
        else if (counter2 == 6)         //if player 2's side is empty, sum all stones in p1's side
        {
            for (int i = 0; i < 6; i++)
            {
                sum += holes.get(i);
                holes.set(i, 0);
            }
            sum += holes.get(6);
            holes.set(6, sum); //set hole to have the sum of all the stones on this player's side
            return true;
        }
        
        else
            return false;
    }
    
    /**
     * Used when a player sets their final stone in an empty Mancala, it gets that stone and all of the stones in the
     * opposing player's stones in their opposing pit (the pit on the exact opposite side) and places all of the stones
     * into the current player's Mancala.
     */
    private void captureStones()
    {
        if(holes.get(currHole) == 1 && holes.get(12 - currHole) != 0 
                && currHole != 6 && currHole != 13)
        {
            int location = 12 - currHole;               //gets the location of the opposing player
            int stones = holes.get(location) + 1; //gets stones from the opposing player + 1 from curr player's pit
            
            holes.set(location, 0);
            holes.set(currHole, 0);
            
            
            if(getPlayerTurn() == true)
            {
                stones += holes.get(6);
                holes.set(6, stones);
            }
            else if (getPlayerTurn() == false)
            {
                stones += holes.get(13);
                holes.set(13, stones);
            }
        }
    }
    
    /**
     * Checks which player won the game and produces a string that will display that.
     * @return a string pertaining to the winning player
     */
    public String getWinningPlayer()
    {
        if (holes.get(6) > holes.get(13))
            return "Player 1 Wins! Congrats!";
        else 
            return "Player 2 Wins! Congrats!";
    }
    
    /**
     * Notifies the listeners attached to this model so that viewers will be repainted
     */
    public void notifyListeners()
    {
        for(ChangeListener l : listeners)
        {
            l.stateChanged(new ChangeEvent(this));
        }
    }
}
