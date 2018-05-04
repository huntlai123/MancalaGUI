
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
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
    public static void main(String[] args)
    {
        initialScreen();
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
                        int numStones = Integer.parseInt(input.getText());
                        if(numStones == 3 | numStones == 4)
                        {
                            board = new Board(numStones);
                            styleScreen();
                            frame.dispose();
                        }
                        else
                        {
                            JFrame invalidNum = new JFrame();
                            JTextArea alert = new JTextArea("Invalid number of stones, please try again");
                            alert.setEditable(false);
                            alert.setLineWrap(true);
                            alert.setWrapStyleWord(true);
                            JButton okay = new JButton("OK");
                            okay.addActionListener(
                                    new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent e)
                                        {
                                            invalidNum.dispose();
                                        }
                                    });
                            invalidNum.add(alert);
                            invalidNum.add(okay);
                            invalidNum.setPreferredSize(new Dimension(150, 150));
                            invalidNum.setLayout(new BoxLayout(invalidNum.getContentPane(), BoxLayout.Y_AXIS));
                            invalidNum.pack();
                            invalidNum.setLocationRelativeTo(null);
                            invalidNum.setVisible(true);
                        }
                        
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
