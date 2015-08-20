package cl.tesis.dns.section;

import cl.tesis.dns.DnsUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsAnswer {

//    private int aname;
    private int atype;
    private int aclass;
    private int ttl;
    private int rdlength;
    private InetAddress rdata;

    public DnsAnswer(byte[] data, int position) throws UnknownHostException {
        this.atype = DnsUtil.byteArraytoInt(data, position + 2, position + 3);
        this.aclass = DnsUtil.byteArraytoInt(data, position + 4, position + 5);
        this.ttl = DnsUtil.byteArraytoInt(data, position + 6, position + 9);
        this.rdlength = DnsUtil.byteArraytoInt(data, position + 10, position + 11);

        byte[] ip = new byte[4];
        System.arraycopy(data, position + 12, ip, 0, 4);
        this.rdata = InetAddress.getByAddress(ip);
    }

    @Override
    public String toString() {
        return "DnsAnswer{" +
                "atype=" + atype +
                ", aclass=" + aclass +
                ", ttl=" + ttl +
                ", rdlength=" + rdlength +
                ", rdata=" + rdata +
                '}';
    }
}
