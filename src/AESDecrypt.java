import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class AESDecrypt extends AES {

    byte[][] cipherInChunks;
    final int ROUNDS =11;

    public AESDecrypt(String keyPath, String inputFilePath, String outputFilePath) {
        super(keyPath, inputFilePath);
        this.outputFilePath = outputFilePath;
        readFiles(keyPath, inputFilePath);

    }

    public void Decrypt() {
        for (int i = 0; i < cipherInChunks.length; i++) {
            generateStateMatrix(Arrays.toString(cipherInChunks[i]));
            addRoundKey(ROUNDS);

        }

    }

    public void readFiles(String keyPath, String cipherPath) {
        byte[] keys = new byte[0];
        Path keyPathObject = Paths.get(keyPath);
        try {
            keys = Files.readAllBytes(keyPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the splitKeys file.");
        }
        splitKeys = SplitIntoChunks(keys);

        Path cipherPathObject = Paths.get(cipherPath);
        try {
            cipherText = Files.readAllBytes(cipherPathObject);
        } catch (Exception e) {
            System.out.println("Failed to read the cipher file.");
        }
        cipherInChunks = SplitIntoChunks(cipherText);

    }
}
