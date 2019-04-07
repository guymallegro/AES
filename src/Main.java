public class Main {


    public static void main(String args[]) {
        // String[] flags = args;
        String[] flags = {"-d", "-k", "/home/guy/Desktop/Projects/test/test_keys", "-i", "/home/guy/Desktop/Projects/test/cipher_short", "-o", "/home/guy/Desktop/Projects/test/test_output"};
        //String[] flags = {"-b", "-m", "/home/guy/Desktop/Projects/test/message_short", "-c", "/home/guy/Desktop/Projects/test/cipher_short", "-o", "/home/guy/Desktop/Projects/test/test_keys"};
        switch (flags[0]) {
            case "-e":
                Encryption(flags);
                break;
            case "-d":
                Decryption(flags);
                break;
            case "-b":
                Hack(flags);
                break;
        }

    }

    private static void Decryption(String[] flags) {
        String keyPath = "", inputFilePath = "", outPutFilePath = "";
        for (int i = 1; i < flags.length; i++) {
            switch (flags[i]) {
                case "-k":
                    keyPath = flags[i + 1];
                    break;
                case "-i":
                    inputFilePath = flags[i + 1];
                    break;
                case "-o":
                    outPutFilePath = flags[i + 1];
                    break;
            }
        }
        AESDecrypt decrypt = new AESDecrypt(keyPath, inputFilePath, outPutFilePath);
        decrypt.Decrypt();
    }

    private static void Hack(String[] flags) {
        String plainTextPath = "", cipherTextPath = "", outPutFilePath = "";
        for (int i = 1; i < flags.length; i++) {
            switch (flags[i]) {
                case "-m":
                    plainTextPath = flags[i + 1];
                    break;
                case "-c":
                    cipherTextPath = flags[i + 1];
                    break;
                case "-o":
                    outPutFilePath = flags[i + 1];
                    break;
            }
        }
        AESHack hack = new AESHack(plainTextPath, cipherTextPath, outPutFilePath);
        hack.Hack();
    }

    private static void Encryption(String[] flags) {
        String keyPath = "", inputFilePath = "", outPutFilePath = "";
        for (int i = 1; i < flags.length; i++) {
            switch (flags[i]) {
                case "-k":
                    keyPath = flags[i + 1];
                    break;
                case "-i":
                    inputFilePath = flags[i + 1];
                    break;
                case "-o":
                    outPutFilePath = flags[i + 1];
                    break;
            }
        }
        AESEncrypt encrypt = new AESEncrypt(keyPath, inputFilePath, outPutFilePath);
        encrypt.Encrypt();
    }
}
