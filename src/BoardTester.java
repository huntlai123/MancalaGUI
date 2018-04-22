
import java.util.Scanner;


/**
 *
 */
public class BoardTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	// BoardFrame myFrame = new BoardFrame(); // Reference to board
		
		// Create reference to buttons/textfields that will take input from user
		// Then pass them by reference to initialPanel
		
		// InitialPanel initialPanel = new InitialPanel();
		// myFrame.add(initialPanel);
		
		// Using InitialPanel object, take input from user who will decide which of the two styles the board
		// will use for the game. 
		
		// BoardStyle myStyle;
		// Process the data taken as input by the InitialPanel object.
		// If Boarstyle1 was chosen, initialize myStyle with BoardStyle1 with the chosen number of stones.
		// else if BoardStyle2 was chosen, initialize myStyle with BoardStyle2 with the chosen number of stones.
    	
    	// Declare and initialize Board object. Set references of listeners from Panel to Board object. 
    	Board board;
        InitialScreen initial = new InitialScreen(board);
        
        /*
        Scanner sc = new Scanner(System.in);
        String input;
        boolean valid = false;
        Board myBoard = null;
                
        do{
                System.out.println("Please enter the initial number of stones in each hole (3 or 4): ");
                input = sc.nextLine();
                input = input.trim();
                try{
                    valid = Integer.valueOf(input) == 3 || Double.valueOf(input) == 4;
                    if (!valid)
                    {
                        System.out.println("Sorry, you must enter either 3 or 4.");
                        continue;
                    }

                    myBoard = new Board(Integer.valueOf(input));
                }
                catch(NumberFormatException e)
                {
                    System.err.println(e);
                    System.err.println("Format Exception error");
                }
            }while(!valid);
        
        myBoard.updateBoard(4);  //testing
        System.out.println(myBoard.getCircularList());  //testing
        */
    }
    
}
