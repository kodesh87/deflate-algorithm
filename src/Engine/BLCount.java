
class BLCount {

    int[] bl_count = new int[10];
    int[] next_code = new int[10];
    String[] code1 = new String[287];
    int code = 0;

    public BLCount() {
        bl_count[7] = 24;
        bl_count[8] = 152;
        bl_count[9] = 112;
    }

    public static void main(String[] args) {
        BLCount newcount = new BLCount();
        newcount.routine();
    }

    public void routine() {
        bl_count[0] = 0;
        for (int bits = 1; bits <= 9; bits++) {
            code = (code + bl_count[bits - 1]) << 1;
            System.out.println("code " + bits + " = " + code);
            next_code[bits] = code;
        }

        // for (int n = 1; n <= 4; n++) {
        // System.out.println(next_code[n]);
        // }
        int count = 0;

        for (int i = 0; i <= 143; i++) {
            code1[i] = leftPadder(Integer.toString(next_code[8]++, 2), 8);
        }

        for (int i = 144; i <= 255; i++) {
            code1[i] = leftPadder(Integer.toString(next_code[9]++, 2), 9);
        }

        for (int i = 256; i <= 279; i++) {
            code1[i] = leftPadder(Integer.toString(next_code[7]++, 2), 7);
        }

        for (int i = 280; i < 287; i++) {
            code1[i] = leftPadder(Integer.toString(next_code[8]++, 2), 8);
        }

        for (int i = 0; i < 287; i++) {
            System.out.println(i + " - " + code1[i]);
        }
    }

    String leftPadder(String txt, int n) {
        while (txt.length() < n) {
            txt = "0" + txt;
        }
        return txt;
    }

    /* public static void main(String[] args) {
		int HIGHEST_ARRAY = 5; //0..4
		int code = 0;
		int[] bl_count = new int[HIGHEST_ARRAY];
		int[] next_count = new int[HIGHEST_ARRAY];
		int[] next_code = new int[HIGHEST_ARRAY];
		
		//initialization
		bl_count[0] = 0;
		bl_count[1] = 0;
		bl_count[2] = 1;
		bl_count[3] = 5;
		bl_count[4] = 2;
		
		int HIGHEST_LENGTH = HIGHEST_ARRAY - 1;
		
		for (int bits = 1; bits <= HIGHEST_LENGTH; bits++) {
			code = (code + bl_count[bits-1]) << 1;
			next_code[bits] = code;
		}
		
		int TOTAL_DISTINCT_CHAR = 8;
		
		//manipulate tree
		
		Tree[] tree;
		tree = new Tree[TOTAL_DISTINCT_CHAR];
		
		// tree[0].Len = 3;
		
		tree[0] = new Tree();
		tree[1] = new Tree();
		tree[2] = new Tree();
		tree[3] = new Tree();
		tree[4] = new Tree();
		tree[5] = new Tree();
		tree[6] = new Tree();
		tree[7] = new Tree();
		
		tree[0].Len = 3;
		tree[1].Len = 3;
		tree[2].Len = 3;
		tree[3].Len = 3;
		tree[4].Len = 3;
		tree[5].Len = 2;
		tree[6].Len = 4;
		tree[7].Len = 4;
		
		int len = 0;
		
		for (int n = 0; n <= TOTAL_DISTINCT_CHAR-1; n++) {
			len = tree[n].Len;
			if (len != 0) {
				tree[n].Code = next_code[len];
				next_code[len]++;
			}
		}
		
		for (int n = 0; n <= TOTAL_DISTINCT_CHAR-1; n++) {
			System.out.println(Integer.toString(tree[n].Code,2));
		}
	} */
}
