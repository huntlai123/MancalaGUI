import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InitialScreen extends JFrame
{
    private Board board;
    
    InitialScreen(Board board)
    {   
        this.board = board;
        JPanel panel = new JPanel();
        
        JTextField input = new JTextField();
        JButton play = new JButton("Next");
        JTextArea prompt = new JTextArea("Number of Stones(3 or 4):");
        
        play.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        StyleScreen styleChoice = new StyleScreen();
                        board
                    }
                });
        
        panel.add(prompt);
        panel.add(input);
        panel.add(play);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        add(panel);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
}
