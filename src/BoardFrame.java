import javax.swing.JFrame;

public class BoardFrame extends JFrame
{
    BoardFrame(BoardPanel panel)
    {
        add(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
