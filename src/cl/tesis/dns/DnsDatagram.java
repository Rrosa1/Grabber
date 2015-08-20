package cl.tesis.dns;

public class DnsDatagram {

    /*
        Dns Header:
                                            1  1  1  1  1  1
              0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |                      ID                       |
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |QR|   Opcode  |AA|TC|RD|RA|   Z    |   RCODE   |
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |                    QDCOUNT                    |
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |                    ANCOUNT                    |
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |                    NSCOUNT                    |
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |                    ARCOUNT                    |
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+

     */
    private static byte[] HEADER = DnsUtil.hexStringToByteArray("000001000001000000000000");

    /* Dns Question: fff.uchile.cl
                                            1  1  1  1  1  1
              0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |                                               |
            /                     QNAME                     /
            /                                               /
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |                     QTYPE                     |
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
            |                     QCLASS                    |
            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+

     */
    private static byte[] STANDARD_QUESTION = DnsUtil.hexStringToByteArray("0366666606756368696c6502636c0000010001");

    public static byte[] defaultDatagram() {
        byte [] res =  new byte[HEADER.length + STANDARD_QUESTION.length];
        System.arraycopy(HEADER, 0, res, 0, HEADER.length);
        System.arraycopy(STANDARD_QUESTION, 0, res, HEADER.length, STANDARD_QUESTION.length);
        return res;
    }

}
