
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 *	
 */
public class MancalaTest {

    private static Board board;			// static reference to a Board (model)
    private static BoardPanel panel;	// static reference to a BoardPanel with the chosen style
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	  
        initialScreen();
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
    
    /**
     * Creates the Board Frame using the initialized panel and model.
     */
    public static void createBoard()
    {
        BoardFrame frame = new BoardFrame(panel, board);
        frame.setSize(700, 500);
    }
    
    /**
     * Creates a frame that prompts the user to choose a board style then calls createBoard to create a board
     * using the chosen style.
     */
    public static void styleScreen()
    {
        JFrame frame = new JFrame();
        JTextArea prompt = new JTextArea("Choose a Style:");
        prompt.setEditable(false);
        JButton rect = new JButton("Rectangle");
        JButton circ = new JButton("Circle");
        
        rect.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        panel = new BoardPanel(board, new BoardStyleRectangle());
                        frame.dispose();
                        board.attach(panel);
                        createBoard();
                    }  
                });
        
        circ.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        panel = new BoardPanel(board, new BoardStyleCircle());
                        frame.dispose();
                        board.attach(panel);
                        createBoard();
                    }
                });

        frame.add(prompt);
        frame.add(rect);
        frame.add(circ);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        rect.requestFocusInWindow();
        rect.requestFocus(true);
    }
    
    /**
     * Creates the initial screen frame that prompts the user how many stones each pit will have in the beginning
     * of the game.
     */
    public static void initialScreen()
    {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JTextField input = new JTextField();
        JButton play = new JButton("Next");
        JTextArea prompt = new JTextArea("Number of Stones(3 or 4):");
        prompt.setEditable(false);

        
        play.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        board = new Board(Integer.parseInt(input.getText()));
                        styleScreen();
                        frame.dispose();
                    }
                });
        
        input.addKeyListener(new KeyAdapter() 
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    board = new Board(Integer.parseInt(input.getText()));
                    styleScreen();
                        frame.dispose();
                }
                        
            }
        });
        
        panel.add(prompt);
        panel.add(input);
        panel.add(play);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        input.requestFocusInWindow();
        input.requestFocus(true);
    }
    
}