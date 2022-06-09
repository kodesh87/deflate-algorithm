
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class DeflateSplash extends JWindow {

    private JLabel splashImage;
    private Dimension screensize;
    private JProgressBar progressBar;
    private int progress = 0;
    private int waitTime = 7;

    public DeflateSplash(JFrame parent) {
        super(parent);

        splashImage = new JLabel(new ImageIcon(getClass().getResource(
                "resources/images/def_splash.jpg")));
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);

        getContentPane().add(splashImage, BorderLayout.CENTER);
        Border border = BorderFactory.createTitledBorder("Loading...");
        progressBar.setStringPainted(true);
        progressBar.setBorder(border);
        getContentPane().add(progressBar, BorderLayout.SOUTH);
        pack();

        screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600, 500);

        setLocation((screensize.width / 2) - (getSize().width / 2),
                (screensize.height / 2) - (getSize().height / 2));

        splashImage.setBorder(BorderFactory.createLineBorder(
                new Color(75, 75, 75)));
        setVisible(true);
        //doSplashStuff();
    }

    public void doSplashStuff() {

        final int pause = waitTime;

        final Runnable updateRunner = new Runnable() {
            public void run() {
                progressBar.setValue(progress);
            }
        };

        final Runnable closerRunner = new Runnable() {
            public void run() {
                setVisible(false);
                dispose();
            }
        };

        Runnable waitRunner = new Runnable() {
            public void run() {
                try {
                    int sec = pause;
                    for (int i = 0; i < sec; i++) {
                        progress += Math.round(Math.random() * (100 / sec) + 1);
                        progress = Math.min(100, progress);
                        if (i >= sec - 1) {
                            progress = 100;
                        }
                        SwingUtilities.invokeLater(updateRunner);
                        Thread.sleep(1000);

                    }
                    //Thread.sleep(1000); //one more for luck ;)
                    SwingUtilities.invokeAndWait(closerRunner);
                } catch (Exception e) {
                }
            }

        };

        Thread splashThread = new Thread(waitRunner, "SplashThread");
        splashThread.start();
    }
}
