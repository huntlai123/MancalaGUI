import java.util.ArrayList;
import javax.swing.event.ChangeListener;

/**
 *
 */
public class Board {
    private final int MAXUNDOS = 3; //maximum number of undos that a player can do
    private boolean playerTurn; //false = player 1's turn, true = player 2's turn
    private int undoCounter;    //simple counter that resets after each player ends their turn
    private CircularList<Integer> holes = null;    //ArrList used to represent the holes in the board including each player's Mancala
    private int currHole = 0;   //current hole to receive stones
    private int lastTurn = 0;   //number of stones moved last turn
    private ArrayList<ChangeListener> listeners;
    
    /**
     * Default constructor. Builds an empty board. 
     */
    public Board()
    {
        playerTurn = false;
        undoCounter = 0;
        listeners = new ArrayList<ChangeListener>();
        holes = new CircularList<Integer>();   //in order to make this circular, need to extend arraylist or create our own circular array
        for (int i = 0; i < 14; i++)
        {
            if(i == 6 || i == 13)
                holes.add(0);
            else
                holes.add(0);
        }
    }
    
    /**
     * Constructor that builds the board with the given number of stones
     * @param stoneNum number of stones to be initialized with the board
     */
    public Board(int stoneNum)
    {
        playerTurn = false;
        undoCounter = 0;
        listeners = new ArrayList<ChangeListener>();
        holes = new CircularList<Integer>();   //in order to make this circular, need to extend arraylist or create our own circular array
        for (int i = 0; i < 14; i++)
        {
            if(i == 6 || i == 13)
                holes.add(0);
            else
                holes.add(stoneNum);
        }
    }
    
    /**
     * Takes the number of stones in the given hole and distributes them to every
     * hole after it. Ignores the opposing player's Mancala
     * @param holeNum the number of the hole to take stones from
     */
    public void updateBoard(int holeNum)  //might need to add the initial hole as a param
    {
        //logic: place stone in each hole starting from the hole after the chosen hole
        //make sure to ignore the opposing player's Mancala        
        currHole = holeNum; //gets the hole to add a stone into
        lastTurn = 0; //reset number of stones used in the turn
        int numStones = holes.get(currHole);    //take stones from this hole
        holes.set(currHole, 0); //set hole to have 0 stones
        while(numStones > 0)
        {
            currHole = (currHole + 1) % 14;
            if (skipMancala())
                continue;
            incArrVal(currHole);
            numStones--;
            lastTurn++;
        }
        if (!takeAnotherTurn())
            changePlayerTurn();
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
     * Checks if the Mancala should have a stone removed when undoing a turn. It skips the Mancala of the 
     * opposing player
     * @return true if the current Mancala shouldn't be skipped
     */
    private boolean skipRetMancala()
    {
        if (currHole % 14 == 6 && playerTurn == false)
            return true;
        else if (currHole % 14 == 13 && playerTurn == true)
            return true;
        else
            return false;
    }
    
    /**
     * Checks if the current player's can take another turn. Player must place final stone into their 
     * own Mancala to make this happen.
     * @return true if the last stone was placed in the current player's Mancala
     */
    private boolean takeAnotherTurn()
    {
        if (currHole % 14 == 6 && playerTurn == true)
            return true;
        else if (currHole % 14 == 13 && playerTurn == false)
            return true;
        else
            return false;
    }
    
    /**
     * Increments the value at the given hole by 1. 
     * @param i location of hole to be changed
     */
    private void incArrVal(int i)
    {
        Integer value = holes.get(i);
        value++;
        holes.set(i, value);
    }
    
    /**
     * Changes to the next player turn
     */
    public void changePlayerTurn()
    {
        playerTurn = !playerTurn;
//        undoCounter = 0; 
        /*
        NOTE the above line should be added when the next player takes their turn and not when
        the current player ends their turn since they have time to undo until the next player 
        makes a move
        */
    }
    
    /**
     * Undoes the player's last move. Checks if the player can undo, if so, it removes all 
     * stones that were distributed and returns them to the original position.
     */
    public void undo()
    {
        int numStones = 0;

        if (canUndo())
        {
            currHole++; //inc by 1 to avoid logic error in loop
            do{
                currHole = (currHole + 13) % 14;    //decrements currHole pointer by 1
                lastTurn--;
                if (skipRetMancala()){
                    lastTurn++;
                    continue;
                }
                numStones++;
                decArrVal(currHole);
                
            }while(lastTurn >= 0);
            numStones += holes.get(currHole); //finds current val and adds the stones to be restored
            holes.set(currHole, numStones); //restores stones
        }
        
    }
    
    /**
     * Decrements the value at the given hole by 1
     * @param i location of hole to be changed
     */
    private void decArrVal(int i)
    {
        Integer value = holes.get(i);
        value--;
        holes.set(i, value);
    }
    
    /**
     * Checks if a player is allowed to undo. A player can only undo 3 times per his/her turn
     * @return true if undo is allowed
     */
    private boolean canUndo()
    {
        if (undoCounter < 3)
            return true;
        else
            return false;
    }
    
    /**
     * Gets the current player's turn. 
     * @return false for player 1, true for player 2
     */
    public boolean getPlayerTurn(){return playerTurn;}
    
    /**
     * Gets the number of undos for the current player
     * @return the number of undos left for this player's turn
     */
    public int getUndoCounter(){return undoCounter;}
    
    /**
     * Gets the circular ArrayList for the holes
     * @return the pit ArrayList
     */
    public CircularList<Integer> getCircularList()
    {
        return holes;
    }
    
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
            }
            sum += holes.get(6);
            holes.set(6, sum); //set hole to have the sum of all the stones on this player's side
            return true;
        }
        
        else
            return false;
    }
}
