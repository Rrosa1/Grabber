package cl.tesis.tls;

import java.util.Random;


public class TLSUtil {
    private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static int countBytes(String s) {
        return s.length() / 2;
    }

    public static String getRandomBytes(int numberOfBytes) {
        byte[] b = new byte[numberOfBytes];
        new Random().nextBytes(b);

        return bytesToHex(b);
    }

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String intToHex(int value, int numberOfBytes) {
        String res = Integer.toHexString(value);
        while (res.length() < (numberOfBytes * 2)) {
            res = "0" + res;
        }
        return res;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static void printHexByte(byte[] array, int read){
        for (int i = 0; i < read ; i++) {
            System.out.print(String.format("%02X", array[i]));
        }
        System.out.println();
    }

    public static String byteArrayToHexString(byte[] array, int start, int end) {
        String res = "";

        for (int i = start; i < end; ++i) {
            res += String.format("%02X", array[i]);
        }

        return res;
    }

    public static int bytesToInt(byte high, byte low) {
        return (high & 0xFF) << 8 | (low & 0xFF);
    }
}
