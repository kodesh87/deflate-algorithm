package Engine;

public class PrefixTree {

    String[] hCodes = new String[288];
    StaticTree stcTree;
    HuffmanNode parent;

    int[] bl_count = new int[10];
    int[] next_code = new int[10];
    int code = 0;

    //LENGTH
    static final byte[] _length_code = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 12, 12,
        13, 13, 13, 13, 14, 14, 14, 14, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16,
        17, 17, 17, 17, 17, 17, 17, 17, 18, 18, 18, 18, 18, 18, 18, 18, 19, 19, 19, 19,
        19, 19, 19, 19, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20,
        21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 22, 22, 22, 22,
        22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 23, 23, 23, 23, 23, 23, 23, 23,
        23, 23, 23, 23, 23, 23, 23, 23, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24,
        24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24,
        25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
        25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 26, 26, 26, 26, 26, 26, 26, 26,
        26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
        26, 26, 26, 26, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27,
        27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 28
    };

    static final int[] base_length = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 14, 16, 20, 24, 28, 32, 40, 48, 56,
        64, 80, 96, 112, 128, 160, 192, 224, 0
    };

    static final int[] extra_lbits = {
        0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0
    };

    //DISTANCE
    static final byte[] _dist_code = {
        0, 1, 2, 3, 4, 4, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8,
        8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10,
        10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
        11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
        12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13,
        13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
        13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14,
        14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14,
        14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14,
        14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15,
        15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
        15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
        15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 0, 0, 16, 17,
        18, 18, 19, 19, 20, 20, 20, 20, 21, 21, 21, 21, 22, 22, 22, 22, 22, 22, 22, 22,
        23, 23, 23, 23, 23, 23, 23, 23, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24,
        24, 24, 24, 24, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
        26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
        26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 27, 27, 27, 27, 27, 27, 27, 27,
        27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27,
        27, 27, 27, 27, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28,
        28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28,
        28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28,
        28, 28, 28, 28, 28, 28, 28, 28, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29,
        29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29,
        29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29,
        29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29
    };

    static final int[] base_dist = {
        0, 1, 2, 3, 4, 6, 8, 12, 16, 24,
        32, 48, 64, 96, 128, 192, 256, 384, 512, 768,
        1024, 1536, 2048, 3072, 4096, 6144, 8192, 12288, 16384, 24576
    };

    static final int[] extra_dbits = {
        0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13
    };

    PrefixTree() {
        stcTree = new StaticTree();
        bl_count[7] = 24;
        bl_count[8] = 152;
        bl_count[9] = 112;
        buildTree();
        getHuffmanCodes(stcTree.root);
    }

    //DEBUG
    // public static void main(String[] args) {
    // PrefixTree pre = new PrefixTree();
    // pre.buildTree();
    // pre.getHuffmanCodes(pre.stcTree.root);
    // pre.print();
    // System.out.println(_length_code[163]);
    // System.out.println(base_length[25]);
    // System.out.println(extra_lbits[25]);
    // pre.getTheOutputLength(220);
    // int j = pre.d_code(8191); //the real distance -1... if 12210, real value is 12211
    // System.out.println(j);
    // System.out.println(base_dist[j]);
    // System.out.println(extra_dbits[j]);
    // System.out.println(pre.getTheOutputLength(163));
    // }
    public String getTheOutputLength(int number) {
        int manipulate = number - 3;
        int index = _length_code[manipulate];
        int baseLength = base_length[index] + 3;
        int extraBit = extra_lbits[index];
        int edoc = 257 + index;
        String extra = "";

        if (extraBit != 0) {
            extra = leftPadder(Integer.toString((number - baseLength), 2), extraBit);
        }

        // Debug
        // System.out.println("getTheOutputLength(): index: " + index + ", baseLength: " + baseLength + ", extraBit: " + extraBit +
        // ", edoc: " + edoc + ", strExtraBit: " + extra + ", edoc @ Huffman: " + hCodes[edoc]);
        return hCodes[edoc] + extra;
    }

    public int getBaseLength(int index) {
        return base_length[index] + 3;
    }

    public int getExtraBit(int index) {
        return extra_lbits[index];
    }

    public int getBaseLengthD(int index) {
        return base_dist[index] + 1;
    }

    public int getExtraBitD(int index) {
        return extra_dbits[index];
    }

    public String getTheOutputDistance(int number) {
        // int j = pre.d_code(8191); //the real distance -1... if 12210, real value is 12211
        // System.out.println(j);
        // System.out.println(base_dist[j]);
        // System.out.println(extra_dbits[j]);
        int manipulate = number - 1;
        int index = d_code(manipulate);
        int baseLength = base_dist[index] + 1;
        int extraBit = extra_dbits[index];

        String fixed = leftPadder(Integer.toString(index, 2), 5);
        String extra = "";

        if (extraBit != 0) {
            extra = leftPadder(Integer.toString((number - baseLength), 2), extraBit);
        }

        return fixed + extra;
    }

    public void initialization() {
        parent = stcTree.root;
    }

    public boolean traceAndFound(String bit) {
        if (parent == null) {
            System.out.println("Node huffman null - Error!");
            return false;
        } else if (parent.left != null && bit.equals("0")) {
            parent = parent.left;
            if (parent.left == null && parent.right == null) {
                return true;
            } else {
                return false;
            }
        } else if (parent.right != null && bit.equals("1")) {
            parent = parent.right;
            if (parent.left == null && parent.right == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int getEDOCFromNode() {
        return parent.edoc;
    }

    int d_code(int dist) {
        System.out.println("dist = " + dist);
        System.out.println("256 + ((dist) >>> 7) = " + (256 + ((dist) >>> 7)));
        return ((dist) < 256 ? _dist_code[dist] : _dist_code[256 + ((dist) >>> 7)]);
    }

    //DEBUG
    public void print() {
        for (int i = 0; i <= 287; i++) {
            System.out.println(i + " - " + hCodes[i]);
        }
    }

    public void buildTree() {
        bl_count[0] = 0;
        for (int bits = 1; bits <= 9; bits++) {
            code = (code + bl_count[bits - 1]) << 1;
            next_code[bits] = code;
        }

        int count = 0;

        for (int i = 0; i <= 143; i++) {
            stcTree.putNewEdoc(i, leftPadder(Integer.toString(next_code[8]++, 2), 8));
        }

        for (int i = 144; i <= 255; i++) {
            stcTree.putNewEdoc(i, leftPadder(Integer.toString(next_code[9]++, 2), 9));
        }

        for (int i = 256; i <= 279; i++) {
            stcTree.putNewEdoc(i, leftPadder(Integer.toString(next_code[7]++, 2), 7));
        }

        for (int i = 280; i <= 287; i++) {
            // System.out.println(i);
            // System.out.println(next_code[8]);
            // System.out.println(next_code[8]++);
            stcTree.putNewEdoc(i, leftPadder(Integer.toString(next_code[8]++, 2), 8));
        }
    }

    public void getHuffmanCodes(HuffmanNode parentNode) {
        if (parentNode == null) {
            return;
        }

        int asciiCode = (int) parentNode.edoc;
        if (parentNode.left == null || parentNode.right == null) {
            if (asciiCode >= 0) {
                hCodes[asciiCode] = parentNode.huffCode;
            }
        }
        if (parentNode.left != null) {
            getHuffmanCodes(parentNode.left);
        }
        if (parentNode.right != null) {
            getHuffmanCodes(parentNode.right);
        }
    }

    String leftPadder(String txt, int n) {
        while (txt.length() < n) {
            txt = "0" + txt;
        }
        // System.out.println(txt);
        return txt;
    }
}
