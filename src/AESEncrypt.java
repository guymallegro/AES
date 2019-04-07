import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class AESEncrypt extends AES {
    private byte[][] originalInChunks;

    AESEncrypt(String keyPath, String inputFilePath, String outputFilePath) {
        super(outputFilePath);
        readFiles(keyPath, inputFilePath);
    }

    void Encrypt() {
        cipherText = new byte[originalText.length];
        int pos = 0;
        for (byte[] originalInChunk : originalInChunks) {
            generateMatrix(originalInChunk, state);
            for (int j = 0; j < ROUNDS; j++) {
                shiftRowsLeft();
                addRoundKey(j);
            }
            for (int j = 0; j < 4; j++) {
                for (int h = 0; h < 4; h++) {
                    cipherText[pos] = state[h][j];
                    pos++;
                }
            }
        }
        WriteResults(true);
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
            originalText = Files.readAllBytes(cipherPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the cipher file.");
        }
        originalInChunks = SplitIntoChunks(originalText);
    }
    private void shiftRowsLeft() {
        for (int i = 1; i < 4; i++) {
            leftRotate(state[i], i);
        }
    }

    private void leftRotate(byte[] arr, int d) {
        for (int i = 0; i < d; i++)
            leftRotatebyOne(arr);
    }

    private void leftRotatebyOne(byte arr[]) {
        byte temp = arr[0];
        for (int i = 0; i < 3; i++)
            arr[i] = arr[i + 1];
        arr[3] = temp;
    }


}
