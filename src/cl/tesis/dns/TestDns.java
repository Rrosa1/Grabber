package cl.tesis.dns;


import java.io.IOException;
import java.net.*;

public class TestDns {

    public static int DNS_PORT = 53;
    public static String DNS_UCHILE = "200.89.70.3";

    public static short ID = 0;

    public static void printShort(short s) {
        System.out.println(String.format("%016d", Integer.parseInt(Integer.toBinaryString(0xFFFF & s))));
    }

    public static void main(String[] args) throws IOException {
        String[] hosts = {"118.167.183.157", "143.95.41.2", "92.63.98.82", "173.237.244.86", "80.190.242.44"};

        DatagramSocket socket = new DatagramSocket(8080);
        socket.setSoTimeout(30000);

        byte[] data = DefaultDnsDatagram.getDefaultDatagram();

        for (int i = 0; i < hosts.length; i++) {
            System.out.println(i);
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(hosts[i]), DNS_PORT),
                           receivePacket = new DatagramPacket(new byte[100], 100);

            socket.send(datagramPacket);

            try {
                socket.receive(receivePacket);
                System.out.println(new DnsDatagramParser(receivePacket.getAddress(), receivePacket.getPort(), receivePacket.getData()).toString());
            } catch (SocketTimeoutException e) {
                System.out.println("TimeOut");
            }
        }
    }
}
