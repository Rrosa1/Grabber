package cl.tesis.dns.section;


import cl.tesis.dns.DnsUtil;

public class DnsHeader {

    private int id;

    private int qr;
    private int opcode;
    private int aa;
    private int tc;
    private int rd;

    private int ra;
    private int z;
    private int ad;
    private int cd;
    private int rcode;

    private int qdcount;
    private int ancount;
    private int nscount;
    private int arcount;

    public DnsHeader() {
    }

    public DnsHeader(byte[] data) {
        this.id = DnsUtil.byteArraytoInt(data, 0, 1);

        this.qr = (data[2] & 0x80) >> 7;
        this.opcode = (data[2] & 0x78) >> 3;
        this.aa = (data[2] & 0x04) >> 2;
        this.tc = (data[2] & 0x02) >> 1;
        this.rd = data[2] & 0x01;

        this.ra = (data[3] & 0x80) >> 7;
        this.z = (data[3] & 0x40) >> 6;
        this.ad = (data[3] & 0x20) >> 5;
        this.cd = (data[3] & 0x10) >> 4;
        this.rcode = (data[3] & 0x0f);

        this.qdcount = DnsUtil.byteArraytoInt(data, 4, 5);
        this.ancount = DnsUtil.byteArraytoInt(data, 6, 7);
        this.nscount = DnsUtil.byteArraytoInt(data, 8, 9);
        this.arcount = DnsUtil.byteArraytoInt(data, 10, 11);

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQr(int qr) {
        this.qr = qr;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public void setAa(int aa) {
        this.aa = aa;
    }

    public void setTc(int tc) {
        this.tc = tc;
    }

    public void setRd(int rd) {
        this.rd = rd;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setAd(int ad) {
        this.ad = ad;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public void setQdcount(int qdcount) {
        this.qdcount = qdcount;
    }

    public void setAncount(int ancount) {
        this.ancount = ancount;
    }

    public void setNscount(int nscount) {
        this.nscount = nscount;
    }

    public void setArcount(int arcount) {
        this.arcount = arcount;
    }

    @Override
    public String toString() {
        return "DnsHeader{" +
                "id=" + id +
                ", qr=" + qr +
                ", opcode=" + opcode +
                ", aa=" + aa +
                ", tc=" + tc +
                ", rd=" + rd +
                ", ra=" + ra +
                ", z=" + z +
                ", ad=" + ad +
                ", cd=" + cd +
                ", rcode=" + rcode +
                ", qdcount=" + qdcount +
                ", ancount=" + ancount +
                ", nscount=" + nscount +
                ", arcount=" + arcount +
                '}';
    }

    public static void main(String[] args) {
        DnsHeader header = new DnsHeader(new byte[]{10, 10, -1, -81, -128, 1, -128, 1, -128, 1, -128, 1});
        System.out.println(header.toString());
    }
}
