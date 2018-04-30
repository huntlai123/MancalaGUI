import javax.swing.JButton;
import javax.swing.JFrame;

public class BoardFrame extends JFrame
{
    BoardFrame(BoardPanel panel)
    {
        JButton undo = new JButton("Undo "+panel.getUndoCount());
        undo.setBounds(1, 1, 100, 50);
        
        undo.addActionListener(event ->
        {
            repaint();
            undo.setText("Undo "+panel.getUndoCount());
            panel.undoLast();
            repaint();
        });
        add(undo);
        add(panel);
        setVisible(true);
        setTitle("Mancala");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
