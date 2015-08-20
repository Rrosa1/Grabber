package cl.tesis.dns.section;

import cl.tesis.dns.DnsUtil;

public class DnsQuestion {

    private String qname;
    private int qtype;
    private int qclass;
    private int finishQuestion;

    public DnsQuestion(byte[] data) {
        this.qname = "";
        int finishUrl = this.parseUrl(data);

        this.qtype = DnsUtil.byteArraytoInt(data, finishUrl + 1, finishUrl + 2);
        this.qclass = DnsUtil.byteArraytoInt(data, finishUrl + 3, finishUrl + 4);

        this.finishQuestion = finishUrl + 5;
    }

    private int parseUrl(byte[] data){
        int i;
        boolean first = true;

        for (i = 12; i < data.length; i++) {
            if (data[i] == 0)
                break;
            else if (first && data[i] <= 31)
                first = false;
            else if (data[i] <= 31)
                this.qname += ".";
            else
                this.qname += (char)data[i];
        }
        return i;
    }

    public int getFinishQuestion() {
        return finishQuestion;
    }

    @Override
    public String toString() {
        return "DnsQuestion{" +
                "qname='" + qname + '\'' +
                ", qtype=" + qtype +
                ", qclass=" + qclass +
                '}';
    }

//    public static void main(String[] args) {
//        byte[] STANDARD_QUESTION = DnsUtil.hexStringToByteArray("0377777706756368696c6502636c0000010001");
//        DnsQuestion question =  new DnsQuestion(STANDARD_QUESTION);
//        System.out.println(question.toString());
//    }

}
