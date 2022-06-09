package Engine;

public interface ISignature {

    final String dSignature = "WIRA";

    //Huffman algorithm
    final short MAXCHARS = 256;

    //LZ77 algorithm
    final int MAXSEARCHWLEN = 32768;
    final short MAXLOOKAHEADWLEN = 258;

    final String strExtension = ".def";

    final short DEFAULT_COMPRESSION = 2;

    final short NO_COMPRESSION = 0;
    final short FAST_COMPRESSION = 1;
    final short NORMAL_COMRESSION = 2;
    final short HIGH_COMPRESSION = 3;

    final int MAXBLOCKSIZE_NOCOMPRESSION = 65535;
    final int HEADERSIZE_NOCOMPRESSION = 5;
}
