package Engine;

import java.io.*;
import FileBitIO.*;

public class Inflater implements ISignature {

    private String fileName, outputFilename;

    private long fileLen = 0, outputFilelen;

    private FileOutputStream outf;
    private String gSummary;

    public Inflater(String txt, String txt2) {
        loadFile(txt, txt2);
    }

    public void loadFile(String txt, String txt2) {
        fileName = txt;
        outputFilename = txt2;
        gSummary = "";
    }

    public boolean decodeFile() throws Exception {
        if (fileName.length() == 0) {
            return false;
        }

        long i;
        int j;
        CFileBitReader fin = new CFileBitReader(fileName);
        fileLen = fin.available();

        String buf = new String(fin._getBytes(dSignature.length()));
        if (!buf.equals(dSignature)) {
            return false;
        }
        outputFilelen = fin.getValueOfBytes(4);
        // System.out.println(outputFilelen);

        gSummary += ("Compressed File Size : " + fileLen + "\n");
        gSummary += ("Original   File Size : " + outputFilelen + "\n");

        try {
            outf = new FileOutputStream(outputFilename);
            i = 0;
            int k, ch, modeCompression, len = 0, tempLen = 0, n = 0;
            String strLen, compStrLen;
            byte[] header = new byte[4], buffer;

            while (i < outputFilelen) {

                modeCompression = (int) fin.getValueOfBytes(1) / 32;
                // System.out.println("mode compression: " + modeCompression);

                switch (modeCompression) {
                    case 0://Block TYPE 000
                        header = fin._getBytes(4);

                        if (!((header[0] == ~header[2]) && (header[1] == ~header[3]))) {
                            throw new Exception("Corrupted File!");
                        }

                        n = MAXBLOCKSIZE_NOCOMPRESSION;

                        buffer = new byte[MAXBLOCKSIZE_NOCOMPRESSION];
                        buffer = fin._getBytes(n);
                        outf.write(buffer, 0, n);

                        i += MAXBLOCKSIZE_NOCOMPRESSION;
                        break;
                    case 4://Block TYPE 100
                        header = fin._getBytes(4);

                        if (!((header[0] == ~header[2]) && (header[1] == ~header[3]))) {
                            throw new Exception("Corrupted File!");
                        }

                        tempLen = header[1];
                        if (tempLen < 0) {
                            tempLen += 256;
                        }
                        len += tempLen;

                        tempLen = header[0];
                        if (tempLen < 0) {
                            tempLen += 256;
                        }
                        len += tempLen << 8;
                        tempLen = len;

                        // System.out.println(len);
                        buffer = new byte[tempLen];
                        buffer = fin._getBytes(tempLen);
                        outf.write(buffer, 0, tempLen);

                        i += tempLen;
                        break;
                    case 1://Block TYPE 001
                        break;
                    case 5://Block TYPE 101
                        int leftLen = (int) fin.available();
                        int oriLen = (int) outputFilelen;
                        PrefixTree tree = new PrefixTree();
                        int edoc,
                         index,
                         baseLength,
                         extraBit,
                         extraValue,
                         length,
                         distance;
                        String prefix5,
                         strExtraBit;
                        Container cont = new Container();

                        //DEEP DEBUG
                        // int count = -1;
                        // String edocStr = ""; String process = "";
                        while (fin.newRemain()) {
                            tree.initialization();

                            //DEEP DEBUG
                            // edocStr = "";
                            // process = fin.newGetBit();
                            // edocStr += process;
                            while (!tree.traceAndFound(fin.newGetBit())) {
                                //DEEP DEBUG
                                // process = fin.newGetBit();
                                // edocStr += process;
                            }

                            // System.out.println("error sini");
                            // System.out.println(fin.newGetBits(16));
                            edoc = tree.getEDOCFromNode();
                            // System.out.println("EDOC: " + edoc);

                            if (edoc >= 0 && edoc <= 255) { //Literal - EDOC
                                outf.write(edoc);
                                cont.fillDictionary(edoc);

                                //DEEP DEBUG
                                // count++;
                                // if ((count >= 30800) && (count <= 32000)) {
                                // System.out.println(count + ". Literal, ascii: " + edoc + ", value: " + edocStr);
                                // }
                            } else if (edoc == 256) {
                                break;
                            } else { //Length Tabel - EDOC
                                index = edoc - 257;
                                baseLength = tree.getBaseLength(index);
                                extraBit = tree.getExtraBit(index);
                                strExtraBit = fin.newGetBits(extraBit);
                                extraValue = Integer.valueOf(strExtraBit, 2);
                                length = baseLength + extraValue; //real value
                                // System.out.println("Index: " + index + ", baselength: " + baseLength +
                                // ", extrabit: " + extraBit +	", strExtraBit: " + strExtraBit + ", real: " + length);

                                //DEEP DEBUG
                                // count += length;
                                // if ((count >= 30800) && (count <= 32000)) {
                                // System.out.print(count + ". Length: " + length + ", value: " +  edocStr + leftPadder(strExtraBit, extraBit));
                                // }
                                prefix5 = fin.newGetBits(5);
                                index = Integer.valueOf(prefix5, 2);
                                baseLength = tree.getBaseLengthD(index);
                                extraBit = tree.getExtraBitD(index);
                                strExtraBit = fin.newGetBits(extraBit);
                                extraValue = Integer.valueOf(strExtraBit, 2);
                                distance = baseLength + extraValue;
                                // System.out.println("Index: " + index + ", baselength: " + baseLength +
                                // ", extrabit: " + extraBit +	", strExtraBit: " + strExtraBit + ", distance: " + distance);

                                //DEEP DEBUG
                                // if ((count >= 30800) && (count <= 32000)) {
                                // System.out.println("-distance: " + distance + ", value: " + (prefix5 + leftPadder(strExtraBit, extraBit)));					
                                // }
                                byte[] buff = new byte[length];
                                cont.fillDictionaryLD(length, distance, buff);
                                outf.write(buff, 0, length);
                            }
                        }
                        i = outputFilelen;

                        break;
                    case 2://Block TYPE 010
                        break;
                    case 6://Block TYPE 110
                        break;
                }
            }

            outf.close();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    String complementBinaryStr(String txt) {
        int i = 0;
        StringBuffer strBuff = new StringBuffer();
        while (i < (txt.length())) {
            if (txt.charAt(i) == '0') {
                strBuff.append("1");
            } else {
                strBuff.append("0");
            }
            i++;
        }
        return new String(strBuff);
    }

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
