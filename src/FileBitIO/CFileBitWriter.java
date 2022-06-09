/* 
 * This code may be freely distributed
 * and used for any non-commericial purpose, as long as its copyright 
 * notice is retained.  The author assumes absolutely no responsibility 
 * for any harm caused by bugs in the code.
 **/

 /* 
 * CFileBitWriter.java - coded By st0le [st0le'n'stuff softwarez!] 
 * Website : http://st0lenc0des.googlepages.com 
 * Copyright (c) st0le 2007 
 */
package FileBitIO;

import java.io.*;

//File Bit Writer - Write to Files BitWise
public class CFileBitWriter {

    private String fileName;

    private File outputFile;
    private FileOutputStream fout;
    private BufferedOutputStream outf;
    private String currBitStr;

    private byte[] buffer = new byte[1024];
    private int total = 0;

    public CFileBitWriter() throws Exception {
        try {
            fileName = "";
            //loadFile(fileName);
        } catch (Exception e) {
            throw e;
        }
        currBitStr = "";
    }

    public CFileBitWriter(String txt) throws Exception {
        try {
            fileName = txt;
            loadFile(fileName);
        } catch (Exception e) {
            throw e;
        }
        currBitStr = "";
    }

    public boolean loadFile(String txt) throws Exception {
        fileName = txt;

        try {
            outputFile = new File(fileName);
            fout = new FileOutputStream(outputFile);
            outf = new BufferedOutputStream(fout);

            currBitStr = "";
            return true;

        } catch (Exception e) {
            throw e;
        }

        //return true;
    }

    public void putBytes(byte[] tempBuffer, int len) throws Exception {
        if ((len + total) > 1024) {
            int diff = 1024 - total;
            System.arraycopy(tempBuffer, 0, buffer, total, diff);
            flush();
            total = len - diff;
            System.arraycopy(tempBuffer, diff, buffer, 0, total);
        } else {
            System.arraycopy(tempBuffer, 0, buffer, total, len);
            total = total + len;
        }
        // outf.write(tempBuffer, 0, len);
    }

    public void forceWriteBytes(byte[] tempBuffer, int len) throws Exception {
        outf.write(tempBuffer, 0, len);
    }

    public void forceWriteByte(int value) throws Exception {
        outf.write(value);
    }
    // public void putByte(int value) throws Exception {
    // outf.write(value);
    // }

    public void _putByte(int value) throws Exception {
        if (isFull()) {
            flush();
        }
        buffer[total] = (byte) value;
        total++;
    }

    public void putBitStr(String str) throws Exception {
        currBitStr += str;
        while (currBitStr.length() >= 8) {
            forceWriteByte(Integer.valueOf(currBitStr.substring(0, 8), 2));
            currBitStr = currBitStr.substring(8);
        }
    }

    public void flush() throws Exception {
        byte[] newBuff = new byte[total]; //Rebuild new array to make sure that the content is match as counted
        System.arraycopy(buffer, 0, newBuff, 0, total);
        // for (int i = 0; i < total; i++) {
        // newBuff[i] = buffer[i];
        // }
        outf.write(newBuff, 0, total);
        total = 0;
    }

    public void forceWriteString(String txt) throws Exception {
        try {
            if (txt.length() > 0) {
                for (int i = 0; i < txt.length(); i++) {
                    outf.write((int) txt.charAt(i));
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void forceWriteValue(long value) throws Exception {
        long newValue = 0;
        for (int i = 0; i < 4; i++) {
            newValue = value;
            newValue <<= (8 * i);
            newValue >>>= (8 * 3);
            // System.out.println(newValue);
            forceWriteByte((int) newValue);
        }
    }

    // public void putBit(int bit) throws Exception{
    // try{
    // bit = bit % 2;
    // currentByte = currentByte + Integer.toString(bit);
    // if(currentByte.length() >= 8){
    // int byteval = Integer.parseInt(currentByte.substring(0,8),2);
    // outf.write(byteval);
    // currentByte = ""; //reset
    // }
    // }catch(Exception e){throw e;}
    // }
    // public void putBits(String bits) throws Exception{
    // try{
    // while(bits.length() > 0){
    // int bit = Integer.parseInt(bits.substring(0,1));
    // putBit(bit);
    // bits = bits.substring(1);
    // }
    // }catch(Exception e){throw e;}
    // }
    // public void putString(String txt) throws Exception{
    // try{
    // while(txt.length() > 0){
    // String binstring = Integer.toString(txt.charAt(0),2);
    // binstring = leftPad8(binstring );
    // putBits(binstring);
    // txt = txt.substring(1);
    // }
    // }catch(Exception e){throw e;}
    // }
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

    boolean isFull() {
        return (total == 1024);
    }

    public void closeFile() throws Exception {
        try {
            //check if incomplete byte exists
            while (currBitStr.length() > 0) {
                putBitStr("1");
            }
            flush();
            outf.close();
        } catch (Exception e) {
            throw e;
        }
    }
}
