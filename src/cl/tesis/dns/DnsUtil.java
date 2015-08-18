package cl.tesis.dns;

public class DnsUtil {

    public static int byteArraytoInt(byte[] data, int start, int end){
        int value = 0;
        for (int i = start; i <= end; i++) {
            value = (value << 8) + (data[i] & 0xff);
        }
        return value;
    }

}
