import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class AESDecrypt extends AES {

    private byte[][] cipherInChunks;

    AESDecrypt(String keyPath, String inputFilePath, String outputFilePath) {
        super(outputFilePath);
        readFiles(keyPath, inputFilePath);

    }

    void Decrypt() {
        originalText = new byte[cipherText.length];
        int pos = 0;
        for (byte[] cipherInChunk : cipherInChunks) {
            generateMatrix(cipherInChunk, state);
            for (int j = ROUNDS - 1; j >= 0; j--) {
                addRoundKey(j);
                shiftRowsRight();
            }
            for (int j = 0; j < 4; j++) {
                for (int h = 0; h < 4; h++) {
                    originalText[pos] = state[h][j];
                    pos++;
                }
            }
        }
        WriteResults(false);
    }

    private void readFiles(String keyPath, String cipherPath) {
        byte[] keys = new byte[0];
        Path keyPathObject = Paths.get(keyPath);
        try {
            keys = Files.readAllBytes(keyPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the splitKeys file.");
        }
        splitKeys = SplitIntoChunks(keys);
        generateMatrix(splitKeys[0], key1);
        generateMatrix(splitKeys[1], key2);
        generateMatrix(splitKeys[2], key3);
        Path cipherPathObject = Paths.get(cipherPath);
        try {
            cipherText = Files.readAllBytes(cipherPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the cipher file.");
        }
        cipherInChunks = SplitIntoChunks(cipherText);

    }

    private void shiftRowsRight() {
        for (int i = 1; i < 4; i++) {
            rightRotate(state[i], i);
        }
    }


    private void rightRotate(byte[] arr, int d) {
        for (int i = 0; i < d; i++)
            rightRotatebyOne(arr);
    }

    private void rightRotatebyOne(byte arr[]) {
        byte temp = arr[3];
        for (int i = 3; i > 0; i--)
            arr[i] = arr[i - 1];
        arr[0] = temp;
    }

}
