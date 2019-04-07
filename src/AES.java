import java.util.Arrays;

public abstract class AES {

    final int CHUNK_SIZE = 128;
    final int MATRIX = 4;
    byte[][] splitKeys;
    String outputFilePath;
    byte[] cipherText;
    byte[] originalText;
    byte[][] state;

    public AES(String keyPath, String inputPath) {
        splitKeys = new byte[MATRIX][MATRIX];
        state = new byte[3][4];
    }

    public byte[][] SplitIntoChunks(byte[] source) {
        byte[][] ret = new byte[(int) Math.ceil(source.length / (double) CHUNK_SIZE)][CHUNK_SIZE];

        int start = 0;

        for (int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(source, start, start + CHUNK_SIZE);
            start += CHUNK_SIZE;
        }
        return ret;
    }

    public void generateStateMatrix(String input) {
        String sb;
        int start = 0;
        int end = 2;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                sb = input.substring(start, end);
                state[j][i] = (byte) (Integer.parseInt(sb, 16));
                start += 2;
                end += 2;
            }
        }
    }

    public void addRoundKey(int rounds) {
        byte[] col;
        int j = 0;
        for (int i = 0; i < 3; i++) {
            col = xorBytes(getColumn(state, i), getColumn(splitKeys, rounds * 4 + j));
            columnCopy(state, i, col);
            j++;
        }

    }

    private byte[] xorBytes(byte[] m, byte[] n) {
        byte[] result = new byte[m.length];
        for (int i = 0; i < m.length; i++)
            result[i] = (byte) (m[i] ^ n[i]);
        return result;
    }

    private byte[] getColumn(byte[][] mat, int pos) {
        byte[] result = new byte[mat.length];
        for (int i = 0; i < 4; i++)
            result[i] = mat[i][pos];
        return result;
    }

    private void columnCopy(byte[][] mat, int pos, byte[] col) {
        for (int i = 0; i < col.length; i++) {
            mat[i][pos] = col[i];
        }
    }


}
