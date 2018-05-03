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
    /*
    public int getUndoCounter1()
    {	return undoCounter1;	}
    */
    /**
     * Returns the number of undoes left for player B.
     * @return the number of undoes left for player B.
     */
    /*
    public int getUndoCounter2()
    {	return undoCounter2;	}
    */
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

        lastTurnFree = false;
        boolean anotherTurn = takeAnotherTurn();
        if (!anotherTurn)
            changePlayerTurn();
        
        lastTurnFree = takeAnotherTurn();
        
        if (lastTurnFree == false)
        {
        	changePlayerTurn();
        }
        
        if (playerTurn != false && currHole >= 0 && currHole <= 5 || playerTurn != true && currHole >= 7 && currHole <= 12)
        {
        	captureStones();
        }
        

        if (undoCounter2 == 3 && !anotherTurn)
        {        	
        	playerTurn = true;
        }
        if (undoCounter1 == 3 && !anotherTurn)
        {        	
        	playerTurn = false;

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
/*
    public void changePlayerTurn()
    {
        playerTurn = !playerTurn;
//        undoCounter = 0; 
 */
        /*
        NOTE the above line should be added when the next player takes their turn and not when
        the current player ends their turn since they have time to undo until the next player 
        makes a move
        */
/*
    }
    */
    /**
     * Undoes the player's last move. Checks if the player can undo, if so, it removes all 
     * stones that were distributed and returns them to the original position.
     */
    /*
    public void undo()
    {
        
        int numStones = 0;
        System.out.println("player turn before undo if = " + playerTurn);
        System.out.println("player undocounter before undo if = " + undoCounter2);
        if (((playerTurn == false && oneCanUndo() && playerTurn == false && undoCounter1 != 3)
                || ((playerTurn == true && undoCounter2 < 3) && (playerTurn == true && twoCanUndo()))) && justUndid == false)
        {
            //System.out.println("last turn " + lastTurn + " items were moved");
            currHole++; //inc by 1 to avoid logic error in loop
            //changePlayerTurn();
            do{
                currHole = (currHole + 13) % 14;    //decrements currHole pointer by 1
                //System.out.println("currHole in do: " + currHole);
                lastTurn--;
                if (skipRetMancala()){
                    //System.out.println("skipped");
                    lastTurn++;
                    continue;
                }
                numStones++;
                decArrVal(currHole);                
                
            }while(lastTurn >= 0);
            
            numStones += holes.get(currHole); //finds current val and adds the stones to be restored
            holes.set(currHole, numStones); //restores stones
            //changePlayerTurn();
            
            if (playerTurn == true)
        	{
        		undoCounter2++;
        		playerTurn = false;
        	}
        	else
        	{
        		undoCounter1++;
        		playerTurn = true;
        	}
            
            //System.out.println("undo counter (in undo)= " + undoCounter);
//            if(undoCounter2 <= 3 && undoCounter2 >= 0)
//                playerTurn = false;
//            if (undoCounter1 <= 3 && undoCounter1 >= 0)
//                playerTurn = true;
//            if (playerTurn == false)
//            {
//                undoCounter1 = 0;
//                undoCounter2++;
//            }
//            else if (playerTurn == true)
//            {
//                undoCounter2 = 0;
//                undoCounter1++;
//            }
            justUndid = true;
            if (lastTurnFree == true){
                changePlayerTurn();
                undoCounter2++;
                undoCounter1++;
            }
            
        }
        System.out.println("player turn after almost everything = " + playerTurn);
//        if (playerTurn == true && oneCanUndo() && undoCounter1 == 2)
//            playerTurn = false;
//        else if (playerTurn == false && twoCanUndo() && undoCounter2 == 2)
//            playerTurn = true;
//        System.out.println(playerTurn == false && twoCanUndo() && undoCounter2 == 2);
//        System.out.println("player turn after everything = " + playerTurn);
        
        notifyListeners();
    }
    */
    /**
     * Returns the number of undos left for the current player
     * @return Number of undos left
     */
    /**
    public int getUndos()
    {
        if (playerTurn == false)
        {
            if (justUndid)
                return (3 - undoCounter2);
            else
                return (3 - undoCounter1);
        }
        else if (playerTurn == true)
        {
            if(justUndid)
                    return (3 - undoCounter1);
            else
                return (3 - undoCounter2);
        }
        else
            return (3 - undoCounter1);
    }
    **/
    
    /**
     * Gets the number of undos used by player 1
     * @return the number of undos used by player 1
     */
    public int getUndoCounter1()
    {
    	return (3 - undoCounter2);
    }
    
    /**
     * Gets the number of undos used by player 2
     * @return the number of undos used by player 2
     */
    public int getUndoCounter2()
    {
    	return (3 - undoCounter1);
    }
    
    /**
     * Decrements the value at the given hole by 1. Updates ChangeListener.
     * @param i location of hole to be changed
     */
    private void decArrVal(int i)
    {
        Integer value = holes.get(i);
        value--;
        holes.set(i, value);
    }
    
    /**
     * Checks if player 1 is allowed to undo. A player can only undo 3 times per his/her turn
     * @return true if undo is allowed
     */
    private boolean oneCanUndo()
    {
    	playerTurn = false;
        System.out.println("1undoC1 = " +undoCounter1);
        System.out.println("1undoC2 = " +undoCounter2);
        if (playerTurn == false && undoCounter2 <= 2 ){
            playerTurn = false;
            //undoCounter1 = 0;
            return true;
        }
        else
            return false;
    }
    
    /**
     * Checks if player 2 is allowed to undo. A player can only undo 3 times per his/her turn
     * @return true if undo is allowed
     */
    private boolean twoCanUndo()
    {
    	playerTurn = true;
        System.out.println("2undoC1 = " +undoCounter1);
        System.out.println("2undoC2 = " +undoCounter2);
        if (playerTurn == true && undoCounter1 <= 2){
            playerTurn = true;
            //undoCounter2 = 0;
            return true;
        }
        else            
            return false;
    }
    
    private boolean changeUndo()
    {
        return false;
    }
    
//    /**
//     * Triggers when the undo counter should be reset
//     * @return true if the counter should be reset
//     */
//    private boolean resetUndoTracker()
//    {
//        if (playerTurn == false)
//            resetUndoCounter++;
//        else if (playerTurn == true)
//            resetUndoCounter++;
//        return false;
//    }
    
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
