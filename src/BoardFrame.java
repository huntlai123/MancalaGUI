import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BoardFrame extends JFrame
{
    BoardFrame(BoardPanel panel, Board board)
    {
        JButton undo = new JButton("Undo "+panel.getUndoCount());
        undo.setBounds(1, 1, 100, 50);
        
        undo.addActionListener(event ->
        {
            repaint();
            undo.setText("Undo " + board.getUndos());
            panel.undoLast();
        });
        
        board.attach(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent e)
                    {
                        undo.setText("Undo " + board.getUndos());
                    }
                });
        
        JTextArea playersTurn = new JTextArea(board.getPlayerTurn() ? "Player 2's turn" : "Player 1's turn");
        playersTurn.setBounds(200, 1, 100, 50);
        board.attach(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent e)
                    {
                        playersTurn.setText(board.getPlayerTurn() ? "Player 2's turn" : "Player 1's turn");
                    }
                });
        
        
        add(playersTurn);
        add(undo);
        add(panel);
        setVisible(true);
        setTitle("Mancala");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
