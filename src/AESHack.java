import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class AESHack extends AES {
    private byte[] keys;


    AESHack(String plainTextPath, String cipherTextPath, String outputPath) {
        super(outputPath);
        readFiles(plainTextPath, cipherTextPath);
        keys = new byte[48];
    }

    private void readFiles(String plainTextPath, String cipherTextPath) {
        Path plainTextPathObject = Paths.get(plainTextPath);
        try {
            originalText = Files.readAllBytes(plainTextPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the plain text file.");
        }
        Path cipherTextPathObject = Paths.get(cipherTextPath);
        try {
            cipherText = Files.readAllBytes(cipherTextPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the cipher file.");
        }
    }

    void Hack() {
        byte[][] plainTextMatrics = new byte[4][4];
        byte[][] cipherTextMatrics = new byte[4][4];
        generateMatrix(originalText, plainTextMatrics);
        generateMatrix(cipherText, cipherTextMatrics);
        key1Matrix = new byte[4][4];
        key2Matrix = new byte[4][4];
        shiftRowsLeft(key2Matrix);
        for (int i = 0; i < 3; i++) {
            shiftRowsLeft(plainTextMatrics);
        }
        xorBytes(plainTextMatrics, cipherTextMatrics, key3Matrix);
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                keys[pos] = key1Matrix[j][i];
                pos++;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                keys[pos] = key2Matrix[j][i];
                pos++;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                keys[pos] = key3Matrix[j][i];
                pos++;
            }
        }
        WriteResults();
    }

    private void WriteResults() {
        String path = outputFilePath;
        byte[] toWrite = keys;
        File file = new File(path);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(toWrite);
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    private void shiftRowsLeft(byte[][] arr) {
        for (int i = 1; i < 4; i++) {
            leftRotate(arr[i], i);
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
