package FileBitIO;

import java.io.*;

//File Bit Reader - Read Files BitWise
public class CFileBitReader {

    private String fileName;

    private File inputFile;
    private FileInputStream fin;
    private BufferedInputStream in;
    private String currentByte, currByte;

    public CFileBitReader() throws Exception {
        try {
            fileName = "";
            //loadFile(fileName);
        } catch (Exception e) {
            throw e;
        }
    }

    public CFileBitReader(String txt) throws Exception {
        try {
            fileName = txt;
            loadFile(fileName);
        } catch (Exception e) {
            throw e;
        }

    }

    public boolean loadFile(String txt) throws Exception {
        fileName = txt;

        try {
            inputFile = new File(fileName);
            fin = new FileInputStream(inputFile);
            in = new BufferedInputStream(fin);
            currentByte = "";
            currByte = "";
            return true;

        } catch (Exception e) {
            throw e;
        }

        //return true;
    }

    String leftPad8(String txt) {
        while (txt.length() < 8) {
            txt = "0" + txt;
        }
        return txt;
    }

    String rightPad8(String txt) {
        while (txt.length() < 8) {
            txt += "0";
        }
        return txt;
    }

    public byte[] _getBytes(int len) throws Exception {
        byte[] buffer = new byte[len];
        in.read(buffer);
        // System.out.println(new String(buffer));
        return buffer;
    }

    public long getValueOfBytes(int len) throws Exception {
        // System.out.println(len);
        byte[] buffer = new byte[len];
        buffer = _getBytes(len);
        // for (int g = 0; g < len; g++) {
        // System.out.println(buffer[g]);
        // }
        // System.out.println(new String(buffer));
        long value = 0;
        int i = 0, currentValue = 0;
        while (--len > -1) {
            currentValue = buffer[len];
            // System.out.println(len);
            if (currentValue < 0) {
                currentValue += 256;
            }
            for (int j = 0; j < i; j++) {
                currentValue = currentValue << 8;
            }
            value += currentValue;
            // System.out.println(value);
            i++;
            // len--;
        }
        return value;
    }

    public String newGetBit() throws Exception {
        try {
            if (currByte.length() == 0 && in.available() >= 1) {
                int _byte = in.read();
                // System.out.println("nilai setiap ambil byte: " + _byte);
                currByte = leftPad8(Integer.toString(_byte, 2));
                // System.out.println("nilai bitStr tsb: " + currByte);
            }
            if (currByte.length() > 0) {
                String ret = currByte.substring(0, 1);
                currByte = currByte.substring(1);
                return ret;
            }
            return "";
        } catch (Exception e) {
            throw e;
        }
    }

    public String newGetBits(int len) throws Exception {
        try {
            if (len == 0) {
                return "0";
            }

            String ret = "";

            for (int i = 0; i < len; i++) {
                ret += newGetBit();
            }
            return ret;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean newRemain() throws Exception {
        if (in.available() > 0 || currByte.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getBit() throws Exception {
        try {
            if (currentByte.length() == 0 && in.available() >= 1) {
                int k = in.read();

                currentByte = Integer.toString(k, 2);
                currentByte = leftPad8(currentByte);
            }
            if (currentByte.length() > 0) {
                String ret = currentByte.substring(0, 1);
                currentByte = currentByte.substring(1);
                return ret;
            }
            return "";
        } catch (Exception e) {
            throw e;
        }
    }

    public String getBits(int n) throws Exception {
        try {
            String ret = "";
            for (int i = 0; i < n; i++) {
                ret += getBit();
            }
            return ret;
        } catch (Exception e) {
            throw e;
        }
    }

    public String getBytes(int n) throws Exception {
        try {
            String ret = "", temp;
            for (int i = 0; i < n; i++) {
                temp = getBits(8);
                char k = (char) Integer.parseInt(temp, 2);
                ret += k;
            }
            return ret;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean eof() throws Exception {
        try {

            return (in.available() == 0);
        } catch (Exception e) {
            throw e;
        }

    }

    public long available() throws Exception {
        try {
            return in.available();
        } catch (Exception e) {
            throw e;
        }
    }

    public void closeFile() throws Exception {
        try {
            in.close();
        } catch (Exception e) {
            throw e;
        }

    }

}
