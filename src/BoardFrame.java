import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Frame that uses BoardPanel, two JPanels, and JButton to create the UI of the game
 * @author Fantastic Four
 *
 */
public class BoardFrame extends JFrame
{
	/**
	 * Creates a BoardFrame with the specified BoardPanel and Board objects.
	 * @param panel BoardPanel object that displays the board game. Acts as both the controller and view.
	 * @param board Board objects that acts as the model of the game.
	 */
    BoardFrame(BoardPanel panel, Board board)
    {
        JPanel control = new JPanel();
        JPanel boardPanel = new JPanel();
        JButton undo = new JButton("Undo");
        
        JLabel undoLabel = new JLabel("Undo(s) left: ");
        JLabel player1UndoCount = new JLabel("Player A: 3     ");
        JLabel player2UndoCount = new JLabel("Player B: 3");
        
        undo.addActionListener(event ->
        {
            board.undo();
        });
        
        board.attach(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent e)
                    {
                    	boolean currentTurn = board.getPlayerTurn();
                    	
                    	if (currentTurn == true)
                    	{
                    		player2UndoCount.setText("Player B: " + board.getUndoCounter2());
                    	}
                    	else
                    	{
                    		player1UndoCount.setText("Player A: " + board.getUndoCounter1() + "     ");
                    	}
                    	
                        revalidate();
                    }
                });
        
        JTextArea playersTurn = new JTextArea(board.getPlayerTurn() ? "Player B's turn" : "Player A's turn");
        playersTurn.setEditable(false);
        board.attach(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent e)
                    {
                        playersTurn.setText(board.getPlayerTurn() ? "Player B's turn" : "Player A's turn");
                        revalidate();
                    }
                });
        
        /**
        JTextArea player1 = new JTextArea("Player 1's Pit");
        JTextArea player2 = new JTextArea("Player 2's Pit");
        player1.setEditable(false);
        player2.setEditable(false);
        **/

        control.add(playersTurn);
        control.add(undo);
        
        control.add(undoLabel);
        control.add(player1UndoCount);
        control.add(player2UndoCount);
        
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.X_AXIS));
        // boardPanel.add(player1);
        boardPanel.add(panel);
        // boardPanel.add(player2);
        
        add(control);
        add(boardPanel);
        setVisible(true);
        setTitle("Mancala");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
