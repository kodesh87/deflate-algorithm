package Engine;

import java.io.*;
import java.util.*;
import FileBitIO.*;

public class Deflater implements ISignature {

    private String fileName, outputFilename;

    CFileBitWriter dFile;
    CFileBitReader rFile;

    String searchWindow, lookAheadWindow;
    int modeCompression;
    String strByte, header;

    long lcount = 0;

    private long fileLen = 0, outputFilelen;
    // private FileInputStream fin;
    // private BufferedInputStream in;	
    private String gSummary;

    static class Config {

        int max_lazy;    // do not perform lazy search above this match length
        int nice_length; // quit search above this match length

        Config(int max_lazy, int nice_length) {
            this.max_lazy = max_lazy;
            this.nice_length = nice_length;
        }
    }

    static final private Config[] config_table;

    static {
        config_table = new Config[4];
        //                         lazy  nice
        config_table[1] = new Config(5, 16);
        config_table[2] = new Config(16, 128);
        config_table[3] = new Config(258, 258);
    }

    //Constructor
    public Deflater(String txt, String txt2, int mode) throws Exception {
        loadFile(txt, txt2);
        modeCompression = mode;
    }

    public void loadFile(String txt, String txt2) throws Exception {
        fileName = txt;
        outputFilename = txt2;
        dFile = new CFileBitWriter(outputFilename);
    }

    public boolean encodeFile() throws Exception {

        if (fileName.length() == 0) {
            return false;
        }

        try {
            rFile = new CFileBitReader(fileName);
            // fin = new FileInputStream(fileName);
            // in = new BufferedInputStream(fin);
        } catch (Exception e) {
            throw e;
        }

        try {
            fileLen = rFile.available();
            if (fileLen == 0) {
                throw new Exception("Isi file kosong!");
            }
            gSummary += ("File Size : " + fileLen + "\n");
        } catch (IOException e) {
            throw e;
        }
        // System.out.println("Ops pertama");
        dFile.forceWriteString(dSignature);
        // System.out.println("Ops pertama");
        dFile.forceWriteValue(fileLen);
        // System.out.println("Ops Kedua");
        if (modeCompression == NO_COMPRESSION) {
            doStore();
        } else {
            doCompression(config_table[modeCompression].max_lazy, config_table[modeCompression].nice_length);
        }

        dFile.closeFile();
        outputFilelen = new File(outputFilename).length();
        float cratio = (float) (((outputFilelen) * 100) / (float) fileLen);
        gSummary += ("Compressed File Size : " + outputFilelen + "\n");
        gSummary += ("Compression Ratio : " + cratio + "%" + "\n");
        return true;
    }

    //Mode: No Compression
    void doStore() throws Exception {
        int i = 0, n = 0;
        String strLen;
        byte[] buffer;
        while (lcount < fileLen) {
            // System.out.println("while pertama");
            if ((fileLen - lcount) >= MAXBLOCKSIZE_NOCOMPRESSION) {
                buffer = new byte[5];
                buffer[0] = (byte) 0;
                buffer[1] = (byte) 255;
                buffer[2] = (byte) 255;
                buffer[3] = (byte) 0;
                buffer[4] = (byte) 0;

                dFile.forceWriteBytes(buffer, 5);

                n = MAXBLOCKSIZE_NOCOMPRESSION;
                buffer = new byte[MAXBLOCKSIZE_NOCOMPRESSION];

                buffer = rFile._getBytes(MAXBLOCKSIZE_NOCOMPRESSION);
                dFile.forceWriteBytes(buffer, MAXBLOCKSIZE_NOCOMPRESSION);

                lcount += MAXBLOCKSIZE_NOCOMPRESSION;
            } else {
                long diff = fileLen - lcount;
                // Sy

                buffer = new byte[5];
                buffer[0] = (byte) 128;
                buffer[1] = (byte) (diff >>> 8);
                // System.out.println(buffer[1]);
                buffer[2] = (byte) (diff % 256);
                // System.out.println(buffer[2]);
                buffer[3] = (byte) ~buffer[1];
                // System.out.println(buffer[3]);
                buffer[4] = (byte) ~buffer[2];
                // System.out.println(buffer[4]);

                dFile.forceWriteBytes(buffer, 5);
                n = (int) diff;
                // System.out.println(diff);

                buffer = new byte[n];

                buffer = rFile._getBytes(n);
                dFile.forceWriteBytes(buffer, n);

                lcount = fileLen;
            }
        }
    }

    //Mode: Compression
    void doCompression(int max_lazy, int nice_length) throws Exception {
        try {
            // System.out.println("Come into doCompression() with mode: " + modeCompression);

            PrefixTree pre = new PrefixTree();
            Container co = new Container();

            int zeroLookAheadW;
            boolean Debug = false;

            long totalUnProcessed = rFile.available();
            // System.out.println(totalUnProcessed);

            dFile.forceWriteByte(160);

            //DEEP DEBUG
            // int count = -1;
            while ((rFile.available() > 0) || (totalUnProcessed != 0)) {

                if ((zeroLookAheadW = co.availableLookAheadW()) <= rFile.available()) {
                    co.fillLookAheadW(rFile._getBytes(zeroLookAheadW));
                } else {
                    co.fillLookAheadW(rFile._getBytes((int) rFile.available()));
                }

                if (Debug) {
                    // co.printSearchW();
                    // co.printLookAheadW();
                    break;
                }

                co.initialization();
                co.trace(nice_length);

                // if (count == 32769) {
                // co.debug = true;
                // co.initialization();
                // co.trace(nice_length);
                // for (int i = 0; i < 9; i++) {
                // System.out.println(co.window[32768 + i]);
                // }
                // for (int i = 0 ; i < 9; i++) {
                // System.out.println(co.window[(32770-1248) + i]);
                // }
                // for (int i = 0; i < 9; i++) {
                // System.out.println(co.window[(32768-1248)] + i);
                // }
                // System.out.println("bestL: " + co.bestL + ", Offset: " + co.bestOffset + ", totalSearchW: " + co.totalSearchW + "freeSearchW: " + co.freeSearchW);
                // co.debug = false;
                // }
                if (co.bestL < 3) {
                    int ascii = (int) co.getLookAheadW(0);
                    if (ascii < 0) {
                        ascii += 256;
                    }
                    dFile.putBitStr(pre.hCodes[ascii]);
                    // if ((count > 32763) && (count <= 33000)) {System.out.println("checkpoint fillSearchW 1");}
                    co.fillSearchW(1);
                    co.delLookAheadW(1);
                    totalUnProcessed--;

                    // DEEP DEBUG
                    // count++;
                    // if ((count >= 32500) && (count <= 33000)) {
                    // System.out.println("start byte ("+ count + ") Literal, ascii: " + ascii + ", value: " + pre.hCodes[ascii]);				
                    // }
                    // if (count == 32764) {
                    // System.out.println("kalan 32764");
                    // }
                } else {
                    if (co.bestL < max_lazy) { //Do lazy search
                        co.lazySearch(nice_length);
                    }
                    if (co.bestLL > (co.bestL + 2)) {
                        int ascii = (int) co.getLookAheadW(0);
                        if (ascii < 0) {
                            ascii += 256;
                        }
                        dFile.putBitStr(pre.hCodes[ascii]);
                        // if ((count > 32763) && (count <= 33000)) {System.out.println("checkpoint fillSearchW 2");}
                        co.fillSearchW(1);
                        co.delLookAheadW(1);
                        totalUnProcessed--;

                        //DEEP DEBUG
                        // count++;
                        // if ((count >= 32500) && (count <= 33000)) {
                        // System.out.println("start byte ("+ count + ") (LAZY) Literal, ascii: " + ascii + ", value: " + pre.hCodes[ascii]);				
                        // }					
                        //DEEP DEBUG
                        // String outLength = pre.getTheOutputLength(co.bestLL);
                        // String outDist = pre.getTheOutputDistance(co.bestOffsetL);
                        // dFile.putBitStr(outLength);
                        // dFile.putBitStr(outDist);
                        dFile.putBitStr(pre.getTheOutputLength(co.bestLL));
                        dFile.putBitStr(pre.getTheOutputDistance(co.bestOffsetL));

                        // if ((count > 32763) && (count <= 33000)) {System.out.println("checkpoint fillSearchW 3");}
                        co.fillSearchW(co.bestLL);
                        co.delLookAheadW(co.bestLL);
                        totalUnProcessed -= co.bestLL;

                        //DEEP DEBUG
                        // count++;
                        // if ((count >= 32500) && (count <= 33000)) {
                        // System.out.println("start byte ("+ count + ") (LAZY) Length: " + co.bestLL + ", value: " +  outLength + "-distance: " + co.bestOffsetL + ", value: " + outDist);					
                        // }
                        // count += co.bestLL-1;
                    } else {
                        //DEEP DEBUG
                        // String outLength = pre.getTheOutputLength(co.bestL);
                        // String outDist = pre.getTheOutputDistance(co.bestOffset);
                        // dFile.putBitStr(outLength);
                        // dFile.putBitStr(outDist);

                        dFile.putBitStr(pre.getTheOutputLength(co.bestL));
                        dFile.putBitStr(pre.getTheOutputDistance(co.bestOffset));

                        // REALLY DEEP DEBUG
                        // if ((count > 32763) && (count <= 33000)) {System.out.println("checkpoint fillSearchW 4");}
                        // if (count == 32764 ) {
                        // for (int i = 0; i < 5; i++) {
                        // System.out.println(co.window[(32768 + 5)  + i]);
                        // }
                        // for (int i = 0; i < 5; i++) {
                        // System.out.println(co.window[(32768 - 6545)  + i]);
                        // System.out.println("will be 1st: ");
                        // }
                        // for (int i = 0; i < 10; i++) {
                        // System.out.println(co.window[i+5]);
                        // System.out.println("end 1st");
                        // }
                        // }
                        co.fillSearchW(co.bestL);
                        co.delLookAheadW(co.bestL);
                        totalUnProcessed -= co.bestL;

                        //DEEP DEBUG
                        // count++;
                        // if (count == 32765 ) {
                        // for (int i = 0; i < 5; i++) {
                        // System.out.println(co.window[(32768 + 5 - 5)  + i]);
                        // }
                        // for (int i = 0; i < 5; i++) {
                        // System.out.println(co.window[(32768 - 6545 - 5)  + i]);
                        // }
                        // for (int i = 0; i < 10; i++) {
                        // System.out.println(co.window[i]);
                        // System.out.println("end 1st");
                        // }
                        // }
                        // if ((count >= 32500) && (count <= 33000)) {
                        // System.out.println("start byte ("+ count + ") Length: " + co.bestL + ", value: " +  outLength + "-distance: " + co.bestOffset + ", value: " + outDist);					
                        // }
                        // count += co.bestL - 1;
                    }
                }
                //DEEP DEBUG
                // if (count > 35200) { 
                // break;
                // }
            }
            dFile.putBitStr(pre.hCodes[256]);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Suplement Code
    String leftPadder(String txt, int n) {
        while (txt.length() < n) {
            txt = "0" + txt;
        }
        return txt;
    }

    String rightPadder(String txt, int n) {
        while (txt.length() < n) {
            txt += "0";
        }
        return txt;
    }

    public String getSummary() {
        return gSummary;
    }
}
