package asteroids.components.gameboard;

import asteroids.AsteroidsGUI;
import asteroids.listeners.EnterListener;

import javax.swing.*;
import java.awt.*;

public class ScoreMenu extends JPanel {

    public static Color bgc = Color.decode("#000c28");
    public static JFormattedTextField textField = new JFormattedTextField();

    public ScoreMenu(AsteroidsGUI gui) {
        //Menu creation
        this.setLayout(null);
        this.setBackground(bgc);

        //title
        JLabel title = new JLabel("Enter your name", SwingConstants.CENTER);
        title.setBounds(100, 200, 800, 60);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Helvetica", Font.BOLD, 40));

        //text field - Setup, adding action listener upon enter
        textField.setColumns(15);
        textField.setBounds(300, 400, 400, 50);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Helvetica", Font.BOLD, 25));
        textField.setBackground(Color.BLACK);
        textField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        textField.addActionListener(new EnterListener(gui));

        //adding title, text field
        this.add(title);
        this.add(textField);
    }

    public static String getText() {
        return textField.getText();
    }

}
