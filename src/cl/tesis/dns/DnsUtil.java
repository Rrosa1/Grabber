package cl.tesis.dns;

public class DnsUtil {

    public static int byteArraytoInt(byte[] data, int start, int end){
        int value = 0;
        for (int i = start; i <= end; i++) {
            value = (value << 8) + (data[i] & 0xff);
        }
        return value;
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

}
