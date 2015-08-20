package cl.tesis.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsDatagramParser {

    private InetAddress address;
    private int port;

    private DnsHeader header;
    private DnsQuestion question;
//    private DnsAnswer answer;

    public DnsDatagramParser(InetAddress address, int port, byte[] data) throws UnknownHostException {
        this.address = address;
        this.port = port;

        this.header = new DnsHeader(data);
        this.question = new DnsQuestion(data);
//        this.answer =  new DnsAnswer(data, this.question.getFinishQuestion());

    }

    @Override
    public String toString() {
        return "DnsDatagramParser{" +
                "address=" + address +
                ", port=" + port +
                ", header=" + header +
                ", question=" + question +
                '}';
    }

    public static void main(String[] args) throws UnknownHostException {
        DnsDatagramParser packet = new DnsDatagramParser(InetAddress.getLocalHost(), 80, new byte[]{10, 10, -1, -81, -128, 1, -128, 1, -128, 1, -128, 1});
        System.out.println(packet.toString());
    }
}
