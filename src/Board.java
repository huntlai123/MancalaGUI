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
    
    public Board()
    {
        playerTurn = false;
        undoCounter = 0;
        holes = new CircularList<Integer>();   //in order to make this circular, need to extend arraylist or create our own circular array
        for (int i = 0; i < 14; i++)
        {
            if(i == 6 || i == 13)
                holes.add(0);
            else
                holes.add(0);
        }
    }
    
    public Board(int stoneNum)
    {
         playerTurn = false;
        undoCounter = 0;
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
    }
    
    /**
     * Checks if the Mancala belongs to the current player or the opposing player. If it belongs to the 
     * current player, don't skip, otherwise skip over that Mancala
     * @return 
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
                if (skipMancala())
                    continue;
                numStones++;
                decArrVal(currHole);
                
                lastTurn--;
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
     * 
     * @return the number of undos left for this player's turn
     */
    public int getUndoCounter(){return undoCounter;}
    
    public CircularList<Integer> getCircularList()
    {
        return holes;
    }
}
