package cl.tesis.dns;

import cl.tesis.dns.section.DnsHeader;
import cl.tesis.dns.section.DnsQuestion;
import cl.tesis.output.ListWritable;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DnsDatagramParser implements ListWritable {

    private InetAddress address;
    private int port;

    private DnsHeader header;
    private DnsQuestion question;

    public DnsDatagramParser(InetAddress address, int port, byte[] data) throws UnknownHostException {
        this.address = address;
        this.port = port;

        this.header = new DnsHeader(data);
        this.question = new DnsQuestion(data);

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

// TODO define csv methods
    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("Ip");
        parameters.add("Validate");
        parameters.add("");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        return null;
    }

    public static void main(String[] args) throws UnknownHostException {
        DnsDatagramParser packet = new DnsDatagramParser(InetAddress.getLocalHost(), 80, new byte[]{10, 10, -1, -81, -128, 1, -128, 1, -128, 1, -128, 1});
        System.out.println(packet.toString());
    }

}
