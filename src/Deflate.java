
import javax.swing.*;

public class Deflate {

    private static void createAndShowGUI() {
        DeflateFrame mainframe = new DeflateFrame();
        mainframe.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Test");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
