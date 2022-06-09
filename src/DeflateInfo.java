
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import FileBitIO.*;

public class DeflateInfo extends JPanel {
    // JLabel splashImage;

    private JTextArea txtStatus;

    DeflateInfo(JFrame parent, boolean isDblBuf) {
        super(isDblBuf);

        setLayout(new BorderLayout());

        JPanel panel = this;
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        // JLabel lblTest;
        panel.setLayout(gridbag);

        constraints.insets = new Insets(3, 3, 3, 3);

        buildConstraints(constraints, 1, 4, 3, 2, 0, 200);
        txtStatus = new JTextArea(5, 50);
        txtStatus.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane logScrollPane = new JScrollPane(txtStatus);
        logScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        gridbag.setConstraints(logScrollPane, constraints);
        panel.add(logScrollPane);
        String text = "DEFLATER v1.0" + "\n"
                + "----------" + "\n"
                + "Aplikasi ini berfungsi untuk melakukan kompresi file, menghasilkan file dengan ekstensi .def" + "\n"
                + "juga melakukan dekompresi file dengan ekstensi .def yang dihasilkan oleh aplikasi ini" + "\n"
                + "\n"
                + "Untuk melakukan kompresi:" + "\n"
                + "1. Pilih file asal melalui file explorer dengan menekan tombol[...] lalu tekan tombol [Open]" + "\n"
                + "   Anda akan memperhatikan bahwa alamat file terisi pada isian 'asal'" + "\n"
                + "   Aplikasi ini otomatis mengisi isian 'tujuan', anda boleh mengabaikan pilihan 'tujuan'." + "\n"
                + "2. Pilih tujuan file melalui file explorer dengan menekan tombol[...] lalu ketik nama file" + "\n"
                + "   yang diinginkan dan menekan tombol[Save]" + "\n"
                + "3. Pilih metode kompresi: STORE, FAST, NORMAL atau HIGH" + "\n"
                + "4. Pilih aksi 'Compress'" + "\n"
                + "5. Tekan tombol[Compress]" + "\n"
                + "\n"
                + "Untuk melakukan dekompresi:" + "\n"
                + "1. Pilih file asal melalui file explorer dengan menekan tombol[...] lalu tekan tombol [Open]" + "\n"
                + "   Anda akan memperhatikan bahwa alamat file terisi pada isian 'asal'" + "\n"
                + "   Aplikasi ini otomatis mengisi isian 'tujuan', anda boleh mengabaikan pilihan 'tujuan'." + "\n"
                + "2. Pilih tujuan file melalui file explorer dengan menekan tombol[...] lalu ketik nama file" + "\n"
                + "   yang diinginkan dan menekan tombol[Save]" + "\n"
                + "3. Pilih aksi 'Decompress'" + "\n"
                + "5. Tekan tombol[Decompress]" + "\n"
                + "\n"
                + "Anda dapat melihat beberapa keterangan tambahan pada bagian Status yang berisi:" + "\n"
                + "a. Lokasi sumber" + "\n"
                + "b. Lokasi tujuan (jika dipilih secara manual)" + "\n"
                + "c. Jenis kompresi (jika terjadi perubahan pilihan)" + "\n"
                + "d. Ukuran sumber" + "\n"
                + "e. Ukuran hasil" + "\n"
                + "f. Rasio kompresi" + "\n"
                + "\n"
                + "Metode kompresi: " + "\n"
                + "1. STORE" + "\n"
                + "   Aplikasi tidak akan melakukan kompresi, namun tetap akan diproses dan menghasilkan file" + "\n"
                + "   dengan ekstensi .def. File ini tidak bisa dibuka secara langsung, namun dapat digunakan" + "\n"
                + "   setelah didekompresi terlebih dahulu" + "\n"
                + "2. FAST, NORMAL, HIGH" + "\n"
                + "   Aplikasi akan melakukan kompresi sesuai metode diatas. Secara berturut-turut dari kiri" + "\n"
                + "   ke kanan, akan berdampak pada rasio kompresi dan lamanya kompresi" + "\n"
                + "   FAST memiliki waktu kompresi lebih kecil dibandingkan NORMAL dan HIGH" + "\n"
                + "        namun memiliki rasio kompresi yang besar" + "\n"
                + "   NORMAL memiliki waktu kompresi lebih kecil dibandingkan HIGH, namun lebih besar" + "\n"
                + "        dibandingkan FAST. Rasio kompresi lebih besar dibandingkan HIGH dan lebih kecil" + "\n"
                + "        dibandingkan FAST" + "\n"
                + "   HIGH memiliki waktu kompresi lebih lama namun rasio kompresi lebih kecil" + "\n"
                + "\n"
                + "Aplikasi ini memiliki keterbatasan dalam melakukan kompresi maupun dekompresi";
        txtStatus.setText(text);

        txtStatus.setEditable(false);
    }

    void buildConstraints(GridBagConstraints gbc, int gx, int gy,
            int gw, int gh, int wx, int wy) {
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = gx;
        gbc.gridy = gy;
        gbc.gridwidth = gw;
        gbc.gridheight = gh;
        gbc.weightx = wx;
        gbc.weighty = wy;
    }
}
