import java.awt.*;
import javax.swing.*;

public class InitialScreen extends JFrame
{
    InitialScreen()
    {   
        JPanel panel = new JPanel();
        
        JTextField input = new JTextField();
        JButton play = new JButton("Play");
        JTextArea prompt = new JTextArea("Number of Stones(3 or 4):");
        
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
