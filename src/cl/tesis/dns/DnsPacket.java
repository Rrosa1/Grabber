package cl.tesis.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsPacket {

    private InetAddress address;
    private int port;

    private DnsHeader header;

    public DnsPacket(InetAddress address, int port, byte[] data) {
        this.address = address;
        this.port = port;

        this.header = new DnsHeader(data);

    }

    @Override
    public String toString() {
        return "DnsPacket{" +
                "address=" + address +
                ", port=" + port +
                ", header=" + header +
                '}';
    }

    public static void main(String[] args) throws UnknownHostException {
        DnsPacket packet = new DnsPacket(InetAddress.getLocalHost(), 80, new byte[]{10, 10, -1, -81, -128, 1, -128, 1, -128, 1, -128, 1});
        System.out.println(packet.toString());
    }
}
