
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Engine.*;

public class DeflateWorkDlg extends JDialog implements ActionListener, DeflateGUIConstants {

    private JFrame owner;
    private JProgressBar prgBar;
    private JButton btnCancel;
    private JLabel lblNote;
    private String gSummary = "";
    private String iFilename, oFilename;
    private boolean bCompress = false;
    private int algoSelected;

    void centerWindow() {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((screensize.width / 2) - (getSize().width / 2),
                (screensize.height / 2) - (getSize().height / 2));
    }

    DeflateWorkDlg(JFrame parent) {
        super(parent, true);
        owner = parent;
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                //setTitle("Thwarted user attempt to close window.");
            }
        });

        setSize(300, 120);
        centerWindow();
        buildDlg();
        setResizable(false);
        btnCancel.addActionListener(this);
        //setVisible(true);
    }

    void buildConstraints(GridBagConstraints gbc, int gx, int gy,
            int gw, int gh, int wx, int wy) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gx;
        gbc.gridy = gy;
        gbc.gridwidth = gw;
        gbc.gridheight = gh;
        gbc.weightx = wx;
        gbc.weighty = wy;
    }

    void buildDlg() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        setLayout(gridbag);
        prgBar = new JProgressBar();
        prgBar.setSize(100, 30);
        prgBar.setStringPainted(false);
        prgBar.setIndeterminate(true);
        btnCancel = new JButton("Cancel");
        lblNote = new JLabel("hahah", JLabel.CENTER);

        constraints.insets = new Insets(3, 3, 3, 3);

        buildConstraints(constraints, 1, 0, 2, 1, 50, 30);
        gridbag.setConstraints(lblNote, constraints);
        add(lblNote);
        buildConstraints(constraints, 0, 1, 4, 1, 100, 40);
        gridbag.setConstraints(prgBar, constraints);
        add(prgBar);
        buildConstraints(constraints, 1, 2, 2, 1, 50, 30);
        constraints.fill = GridBagConstraints.NONE;
        gridbag.setConstraints(btnCancel, constraints);
        add(btnCancel);
    }

    void doWork(String inputFilename, String outputFilename, int mode, int algorithm) {
        String buf;
        File infile = new File(inputFilename);

        //chk if file exists
        if (!infile.exists()) {
            gSummary += "File tidak ada!\n";
            return;
        }
        bCompress = (mode == COMPRESS);
        if (bCompress) {
            lblNote.setText("Compressing " + infile.getName());
        } else {
            lblNote.setText("Decompressing " + infile.getName());
        }

        setTitle(lblNote.getText());

        final int falgo = algorithm;
        iFilename = inputFilename;
        oFilename = outputFilename;
        gSummary = "";

        //Create Thread for Compress/Decompress
        final Runnable closeRunner = new Runnable() {
            public void run() {
                setVisible(false);
                dispose();
            }
        };

        Runnable workingThread = new Runnable() {
            public void run() {
                try {
                    boolean success = false;

                    if (bCompress) {
                        Deflater deflater = new Deflater(iFilename, oFilename, falgo);
                        success = deflater.encodeFile();
                        gSummary += deflater.getSummary();

                    } else {
                        Inflater inflater = new Inflater(iFilename, oFilename);
                        success = inflater.decodeFile();
                        gSummary += inflater.getSummary();
                    }
                } catch (Exception e) {
                    gSummary += e.getMessage();
                }

                try {
                    SwingUtilities.invokeAndWait(closeRunner);
                } catch (Exception e) {
                    gSummary += "\n" + e.getMessage();
                }
            }
        };//working thread

        Thread work = new Thread(workingThread);
        work.start();

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) { //called only when cancel is pressed
        //Object obj = e.getSource();
        dispose();
    }

    public String getSummary() {
        if (gSummary.length() > 0) {
            String line = "----------------------------------------------";
            return line + "\n" + gSummary + line;
        } else {
            return "";
        }

    }
}
