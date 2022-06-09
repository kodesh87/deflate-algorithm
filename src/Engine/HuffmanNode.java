package Engine;

public class HuffmanNode {

    HuffmanNode left, right;
    public int edoc;

    //Code Words
    public String huffCode;

    public HuffmanNode() {
        edoc = -1;
        huffCode = "";
        left = null;
        right = null;
    }

    public HuffmanNode(int xedoc, HuffmanNode lchild, HuffmanNode rchild, String xhuffCode) {
        edoc = xedoc;
        left = lchild;
        right = rchild;
        huffCode = xhuffCode;
    }
}
