import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BoardFrame extends JFrame
{
    BoardFrame(BoardPanel panel, Board board)
    {
        JPanel control = new JPanel();
        JPanel boardPanel = new JPanel();
        JButton undo = new JButton("Undo "+panel.getUndoCount());
        
        undo.addActionListener(event ->
        {
            undo.setText("Undo " + board.getUndos());
            panel.undoLast();
        });
        
        board.attach(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent e)
                    {
                        undo.setText("Undo " + board.getUndos());
                        revalidate();
                    }
                });
        
        JTextArea playersTurn = new JTextArea(board.getPlayerTurn() ? "Player 2's turn" : "Player 1's turn");
        playersTurn.setEditable(false);
        board.attach(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent e)
                    {
                        playersTurn.setText(board.getPlayerTurn() ? "Player 2's turn" : "Player 1's turn");
                        revalidate();
                    }
                });
        
        JTextArea player1 = new JTextArea("Player 1's Pit");
        JTextArea player2 = new JTextArea("Player 2's Pit");
        player1.setEditable(false);
        player2.setEditable(false);

        control.add(playersTurn);
        control.add(undo);
        
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.X_AXIS));
        boardPanel.add(player1);
        boardPanel.add(panel);
        boardPanel.add(player2);
        
        add(control);
        add(boardPanel);
        setVisible(true);
        setTitle("Mancala");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
