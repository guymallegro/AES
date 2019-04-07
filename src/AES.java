import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

public abstract class AES {
    final int ROUNDS = 3;
    private final int CHUNK_SIZE = 16;
    private final int MATRIX = 4;
    byte[][] splitKeys;
    String outputFilePath;
    byte[] cipherText;
    byte[] originalText;
    byte[][] state;
    byte[][] key1;
    byte[][] key2;
    byte[][] key3;


    public AES(String outputPath) {
        splitKeys = new byte[4][4 * (ROUNDS + 1)];
        state = new byte[MATRIX][MATRIX];
        this.outputFilePath = outputPath;
        key1 = new byte[MATRIX][MATRIX];
        key2 = new byte[MATRIX][MATRIX];
        key3 = new byte[MATRIX][MATRIX];


    }

    byte[][] SplitIntoChunks(byte[] source) {
        byte[][] ret = new byte[(int) Math.ceil(source.length / (double) CHUNK_SIZE)][CHUNK_SIZE];
        int start = 0;
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(source, start, start + CHUNK_SIZE);
            start += CHUNK_SIZE;
        }
        return ret;
    }

    void generateMatrix(byte[] input, byte[][] output) {
        int pos = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                output[j][i] = input[pos];
                pos++;
            }
        }
    }

    void addRoundKey(int round) {
        switch (round) {
            case 0:
                xorBytes(state, key1, state);
                break;
            case 1:
                xorBytes(state, key2, state);
                break;
            case 2:
                xorBytes(state, key3, state);
                break;
        }
    }

    public void xorBytes(byte[][] m, byte[][] n, byte[][] dest) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                dest[i][j] = (byte) (m[i][j] ^ n[i][j]);
            }
        }
    }

    void WriteResults(boolean cipher) {
        String path = outputFilePath;
        byte[] toWrite = cipherText;
        if (!cipher) {
            toWrite = originalText;
        }
        File file = new File(path);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(toWrite);
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

}