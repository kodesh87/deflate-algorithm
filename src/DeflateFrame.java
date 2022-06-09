
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class DeflateFrame extends JFrame {

    private DeflateSplash splash;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private DeflateMenu panCompression;
    private DeflateAbout panAbout;
    private DeflateInfo panInfo;
    private JLabel lblBanner;

    void centerWindow() {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((screensize.width / 2) - (getSize().width / 2),
                (screensize.height / 2) - (getSize().height / 2));
    }

    DeflateFrame() {
        setVisible(false);
        splash = new DeflateSplash(this);  //uncomment on release
        splash.doSplashStuff();

        setTitle("Deflater v1.0 by Wira Nov Kurnia Sihombing - 051401087!");
        setSize(600, 500);
        centerWindow();
        setLayout(new BorderLayout(5, 5));

        lblBanner = new JLabel("Deflate Compression", SwingConstants.CENTER);
        lblBanner.setSize(400, 25);
        lblBanner.setFont(new Font("Matura MT Script Capitals", Font.BOLD & Font.ITALIC, 50));

        panCompression = new DeflateMenu(this, false);
        panAbout = new DeflateAbout(this, false);
        panInfo = new DeflateInfo(this, false);

        tabbedPane.addTab("Utama", panCompression);

        tabbedPane.addTab("Informasi", panInfo);

        tabbedPane.addTab("Tentang", panAbout);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        getContentPane().add(lblBanner, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        setResizable(false);

        setVisible(true);
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {

            //System.exit(0); //remove on release
            int exit = JOptionPane.showConfirmDialog(this, "Keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (exit == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            super.processWindowEvent(e);
        }
    }
}
