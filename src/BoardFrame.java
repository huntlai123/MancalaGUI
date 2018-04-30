import javax.swing.JButton;
import javax.swing.JFrame;

public class BoardFrame extends JFrame
{
    BoardFrame(BoardPanel panel)
    {
        JButton reset = new JButton("Reset "+panel.getUndoCount());
        reset.setBounds(1, 1, 100, 50);
        
        reset.addActionListener(event ->
        {
            repaint();
            reset.setText("Reset "+panel.getUndoCount());
            panel.undoLast();
            repaint();
        });
        add(reset);
        add(panel);
        setVisible(true);
        setTitle("Mancala");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
