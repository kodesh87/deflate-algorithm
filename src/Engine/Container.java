package Engine;

public class Container implements ISignature {

    // byte[] searchW = new byte[MAXSEARCHWLEN]; //32768
    // byte[] lookAheadW = new byte[MAXLOOKAHEADWLEN]; //258 
    //search 0..32767 | space 32768 | 32769..33026.
    //MAXSEARCHWLEN + MAXLOOKAHEADWLEN + 1 = 33027
    byte[] window = new byte[MAXSEARCHWLEN + MAXLOOKAHEADWLEN];
    byte[] dictionary = new byte[32768];

    //L stand for Length and Lazy
    int bestL, bestOffset, bestLL, bestOffsetL;
    int totalSearchW, totalLookAheadW, freeDictionary;
    int freeSearchW;
    final int STARTSEARCHW = 0, ENDSEARCHW = 32767;
    final int STARTLOOKAHEADW = 32768, ENDLOOKAHEADW = 33025;
    boolean debug;

    public Container() {
        totalSearchW = 0;
        totalLookAheadW = 0;
        freeSearchW = MAXSEARCHWLEN;
        freeDictionary = 32768;
    }

    void fillDictionary(int literal) {
        System.arraycopy(dictionary, 1, dictionary, 0, 32767);
        dictionary[32767] = (byte) literal;
    }

    void fillDictionaryLD(int length, int distance, byte[] buff) {
        int literal;
        for (int i = 0; i < length; i++) {
            literal = dictionary[32768 - distance];
            buff[i] = (byte) literal;
            fillDictionary(literal);
        }
    }

    //Design for searchW
    //UPDATED COMPOSITE WINDOW
    void fillSearchW(int length) {
        if (freeSearchW == MAXSEARCHWLEN) {
            // System.out.println("Spesial case 0");
            freeSearchW -= length;
            System.arraycopy(window, STARTLOOKAHEADW, window, freeSearchW, length);
            totalSearchW += length;
        } else if (freeSearchW == 0) {
            // System.out.println("Spesial case 1");
            System.arraycopy(window, length, window, 0, (MAXSEARCHWLEN - length));
            System.arraycopy(window, STARTLOOKAHEADW, window, (MAXSEARCHWLEN - length), length);
            // System.out.println("lengh: " + length + ", Jumlah awal: " + (MAXSEARCHWLEN - length) + " tujuan akhir u/ length: " + (totalSearchW - length));
            freeSearchW = 0;
            //If freeSearchW is zero, no need update totalSearchW, because it means totalSearchW always FULL next time
        } else if ((freeSearchW - length) >= 0) {
            // System.out.println("spesial case 2");
            int markStart = freeSearchW;
            freeSearchW -= length;
            System.arraycopy(window, markStart, window, freeSearchW, totalSearchW);
            int markStartLookAheadW = MAXSEARCHWLEN - length;
            System.arraycopy(window, STARTLOOKAHEADW, window, markStartLookAheadW, length);
            totalSearchW += length;
        } else if ((freeSearchW - length) < 0) {
            // System.out.println("spesial case 3");
            int getOnly = MAXSEARCHWLEN - length;
            byte[] newBuff = new byte[getOnly];
            System.arraycopy(window, freeSearchW, newBuff, 0, getOnly);
            System.arraycopy(newBuff, 0, window, 0, getOnly);
            newBuff = new byte[length];
            System.arraycopy(window, STARTLOOKAHEADW, newBuff, 0, length);
            System.arraycopy(newBuff, 0, window, getOnly, length);
            // System.out.println("freeSearch: " + freeSearchW + ", length: " + length + ", getOnly: " + getOnly + ", startLookAhead: " + STARTLOOKAHEADW);
            freeSearchW = 0;
            totalSearchW = MAXSEARCHWLEN;
            // System.out.println("end spesial case 3");
        } else {
            System.out.println("ERROR");
        }
    }

    //Design for looakAheadW, NO NEED UPDATE
    int availableLookAheadW() {
        return MAXLOOKAHEADWLEN - totalLookAheadW;
    }

    //UPDATED COMPOSITE WINDOW
    void fillLookAheadW(byte[] temp) {
        int markStartR;

        if ((totalLookAheadW + temp.length) > MAXLOOKAHEADWLEN) {
            System.out.println("WARNING: OVERLOAD LOOKAHEAD!");
            int free = MAXLOOKAHEADWLEN - totalLookAheadW;
            int markStart = temp.length - free;
            markStartR = markStart + STARTLOOKAHEADW;
            totalLookAheadW -= markStart;
            System.arraycopy(window, markStart, window, STARTLOOKAHEADW, totalLookAheadW);
        }
        markStartR = totalLookAheadW + STARTLOOKAHEADW;
        System.arraycopy(temp, 0, window, markStartR, temp.length);
        totalLookAheadW += temp.length;
    }

    int getLookAheadW(int index) {
        return window[STARTLOOKAHEADW + index];
    }

    //UPDATED COMPOSITE WINDOW
    void delLookAheadW(int length) {
        totalLookAheadW -= length;
        System.arraycopy(window, length + STARTLOOKAHEADW, window, STARTLOOKAHEADW, totalLookAheadW);
    }

    public void printDictionary(int len) {
        byte[] buff = new byte[len];
        System.arraycopy(window, 32768 - len, buff, 0, len);
        System.out.println(new String(buff));
    }

    //UPDATED COMPOSITE WINDOW
    void trace(int length) {
        int count = 0;
        int matchL, count2, currOffset, currPosSearchW, currPosLookAheadW;
        boolean found = false;

        while ((++count <= totalSearchW) && (found != true)) {
            currPosSearchW = MAXSEARCHWLEN - count;

            if (currPosSearchW < freeSearchW) { //crossing the limit
                currPosSearchW++;
                break;
            }

            count2 = 1;
            matchL = 0;
            currOffset = currPosSearchW;
            currPosLookAheadW = STARTLOOKAHEADW;

            // if (debug) {
            // System.out.println("currOffset: " + currOffset);
            // }
            while ((matchL < length) && (currPosLookAheadW < ENDLOOKAHEADW) && (count2 <= totalLookAheadW)) {
                if (window[currPosSearchW] == window[currPosLookAheadW]) {
                    matchL++;
                    currPosSearchW++;
                    currPosLookAheadW++;
                    count2++;
                } else {
                    break;
                }
            }

            if (matchL > bestL) {
                if (debug) {
                    for (int i = 0; i < matchL; i++) {
                        System.out.print(" " + window[currOffset + i] + " " + window[STARTLOOKAHEADW + i] + "-");
                    }
                    System.out.println("Old bestOffset: " + bestOffset + ", currOffset: " + currOffset + "now bestOffset: " + (MAXSEARCHWLEN - currOffset));
                    System.out.println("bestL: " + bestL + ", matchL: " + matchL);
                }
                bestL = matchL;
                bestOffset = MAXSEARCHWLEN - currOffset;
            }

            if (bestL >= length) {
                found = true;
            }
        }
    }

    //UPDATED COMPOSITE WINDOW
    void lazySearch(int length) {
        int count = 0;
        int matchL, count2, currOffset, currPosSearchW, currPosLookAheadW;
        boolean found = false;

        while ((++count <= totalSearchW) && (found != true)) {
            currPosSearchW = MAXSEARCHWLEN - count;

            if (currPosSearchW < freeSearchW) { //crossing the limit
                currPosSearchW++;
                break;
            }

            count2 = 2;
            matchL = 0;
            currOffset = currPosSearchW;
            currPosLookAheadW = STARTLOOKAHEADW + 1; //Skip the 1st Byte for Lazy

            //DISABLE: (currPosSearchW < MAXSEARCHWLEN) && (count2 < MAXLOOKAHEADWLEN) && (matchL < length)
            while ((matchL < length) && (currPosLookAheadW < ENDLOOKAHEADW) && (count2 <= totalLookAheadW)) {
                if (window[currPosSearchW] == window[currPosLookAheadW]) {
                    matchL++;
                    currPosSearchW++;
                    currPosLookAheadW++;
                    count2++;
                } else {
                    break;
                }
            }

            if (matchL > bestLL) {
                bestLL = matchL;
                bestOffsetL = MAXSEARCHWLEN - currOffset + 1;
            }

            if (bestLL >= length) {
                found = true;
            }
        }
    }

    void initialization() {
        bestL = 0;
        bestOffset = 0;
        bestLL = 0;
        bestOffsetL = 0;
    }

    //DEBUG
    void printSearchW() {
        byte[] temp = new byte[totalSearchW];
        System.arraycopy(window, ENDSEARCHW + 1 - totalSearchW, temp, 0, totalSearchW);
        String str = new String(temp);
        System.out.println(str);
        // for (int i = (ENDSEARCHW + 1 - totalSearchW); i < (ENDSEARCHW + 1); i++) {
        // System.out.print(window[i]);
        // System.out.println();
        // }
    }

    void printLookAheadW() {
        byte[] temp = new byte[totalLookAheadW];
        System.arraycopy(window, STARTLOOKAHEADW, temp, 0, totalLookAheadW);
        String str = new String(temp);
        System.out.println(str);
        // for (int i = (STARTLOOKAHEADW); i < (STARTLOOKAHEADW + totalLookAheadW); i++) {
        // System.out.print(window[i]);
        // System.out.println();
        // }
    }
}
