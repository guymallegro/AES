public class Main {


    public static void main(String args[]) {
        String[] flags = args;
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
        AESDecrypt decrypt = new AESDecrypt(keyPath,inputFilePath,outPutFilePath);
        decrypt.Decrypt();

    }

    private static void Hack(String[] flags) {

    }

    private static void Encryption(String[] flags) {

    }

}
