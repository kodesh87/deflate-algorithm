
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DeflateAbout extends JPanel {

    JLabel splashImage;

    DeflateAbout(JFrame parent, boolean isDblBuf) {
        super(isDblBuf);

        setLayout(new BorderLayout());
        splashImage = new JLabel(new ImageIcon(getClass().getResource(
                "resources/images/def_about.jpg")));

        add(splashImage, BorderLayout.CENTER);

        splashImage.setBorder(BorderFactory.createLineBorder(
                new Color(75, 75, 75)));
    }
}
