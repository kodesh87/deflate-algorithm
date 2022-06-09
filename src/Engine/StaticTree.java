package Engine;

public class StaticTree {

    HuffmanNode root;
    int Edoc;
    String prefixCode;

    public StaticTree() {
        root = new HuffmanNode();
        Edoc = -1;
        prefixCode = "";
    }

    public boolean putNewEdoc(int xEdoc, String xprefixCode) {
        Edoc = xEdoc;
        prefixCode = xprefixCode;
        processNode(root, xprefixCode);
        return true;
    }

    public void processNode(HuffmanNode parentNode, String tempPrefixCode) {
        String buff = tempPrefixCode.substring(0, 1);
        if (tempPrefixCode.length() == 1) {
            HuffmanNode newNode = new HuffmanNode(Edoc, null, null, prefixCode);
            if (buff.equals("0") && (parentNode.left == null)) {
                parentNode.left = newNode;
            } else if (buff.equals("1") && (parentNode.right == null)) {
                parentNode.right = newNode;
            }
        } else {
            if (buff.equals("0")) {
                if (parentNode.left == null) {
                    HuffmanNode newNode = new HuffmanNode(-1, null, null, "");
                    parentNode.left = newNode;
                }
                processNode(parentNode.left, tempPrefixCode.substring(1));
            } else {
                if (parentNode.right == null) {
                    HuffmanNode newNode = new HuffmanNode(-1, null, null, "");
                    parentNode.right = newNode;
                }
                processNode(parentNode.right, tempPrefixCode.substring(1));
            }
        }
    }

    //Debug
    /* public void trace(HuffmanNode parent) {
		if (parent.left != null) {
			System.out.println("kiri");
			trace(parent.left);
		}
		if (parent.right != null) {
			System.out.println("kanan");
			trace(parent.right);
		}
	} */
}
