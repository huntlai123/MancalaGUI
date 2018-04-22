import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StyleScreen extends JFrame
{
    StyleScreen()
    {
        JTextArea prompt = new JTextArea("Choose a Style:");
        JButton rect = new JButton("Rectangle");
        JButton circ = new JButton("Circle");
        
        add(prompt);
        add(rect);
        add(circ);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
