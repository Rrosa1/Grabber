package cl.tesis.tls.handshake;


import cl.tesis.tls.TLSUtil;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.util.Arrays;

public class ServerHello {

    public static final int TLS_HEADER_LENGTH = 5;
    public static final int HANDSHAKE_HEADER_LENGTH = 4;
    public static final int RANDOM_LENGTH = 32;
    private static final int SESSION_ID_START = RANDOM_LENGTH + 2;

    private byte[] tlsHeader;
    private byte[] handshakeHeader;
    private byte[] handshakeBody;
    private byte[] protocolVersion;
    private byte[] random;
    private byte[] sessionID;
    private byte[] cipherSuite;
    private byte extension;


    public ServerHello(byte[] array) throws TLSHeaderException, HandshakeHeaderException {
        /* Parsing headers */
        this.tlsHeader = Arrays.copyOfRange(array, 0, TLS_HEADER_LENGTH);
        int tlsBodyLength = (this.tlsHeader[3] & 0xFF) << 8 | (this.tlsHeader[4] & 0xFF);
        if (!checkTLSHeader()) {
            throw new TLSHeaderException("Error in Server Hello header");
        }

        this.handshakeHeader = Arrays.copyOfRange(array, TLS_HEADER_LENGTH, TLS_HEADER_LENGTH + HANDSHAKE_HEADER_LENGTH);
        if (!checkHandshakeHeader())
            throw new HandshakeHeaderException("Error in Server Hello header");
        this.handshakeBody = Arrays.copyOfRange(array, TLS_HEADER_LENGTH + HANDSHAKE_HEADER_LENGTH, tlsBodyLength + TLS_HEADER_LENGTH);

        /* Parsing handshake body */
        this.protocolVersion = new byte[]{this.handshakeBody[0], this.handshakeBody[1]};
        this.random = Arrays.copyOfRange(this.handshakeBody, 2, RANDOM_LENGTH + 2);
        this.sessionID = Arrays.copyOfRange(this.handshakeBody, SESSION_ID_START, SESSION_ID_START + this.handshakeBody[34]);
        this.cipherSuite = new byte[]{this.handshakeBody[35 + this.handshakeBody[34]], this.handshakeBody[36 + this.handshakeBody[34]]};
        this.extension =  this.handshakeBody[this.handshakeBody.length - 1];

    }

    private boolean checkTLSHeader() {
        return this.tlsHeader[0] == 0x16;
    }

    private boolean checkHandshakeHeader() {
        return this.handshakeHeader[0] == 0x02;
    }

    public int endOfServerHello() {
        return TLS_HEADER_LENGTH + HANDSHAKE_HEADER_LENGTH + this.handshakeBody.length;
    }

    public byte[] getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public String toString() {
        return "ServerHello{" +
                "tlsHeader=" + TLSUtil.bytesToHex(tlsHeader) +
                ", handshakeHeader=" + TLSUtil.bytesToHex(handshakeHeader) +
                ", protocolVersion=" + TLSUtil.bytesToHex(protocolVersion) +
                ", random=" + TLSUtil.bytesToHex(random) +
                ", sessionID=" + TLSUtil.bytesToHex(sessionID) +
                ", cipherSuite=" + TLSUtil.bytesToHex(cipherSuite) +
                ", extension=" + String.format("%02x", extension) +
                '}';
    }

    public static void main(String[] args) throws TLSHeaderException, HandshakeHeaderException {
        String response = "160302004A0200004603025609AFC74C4F8ADD0D823EFBDE9E995DECC9FB7A103E82EE1E46AB9963D76DA820EBDF3B821AEF52572A2D648D5FAEEAA292F7756E8FCC57B9C7CE831DECBCA86800330016030203650B00036100035E00035B308203573082023FA003020102020900C65A2EBC81EB41D2300D06092A864886F70D0101050500306B3111300F060355040A130853656E646D61696C31183016060355040B130F53656E646D61696C20536572766572311E301C0603550403131573756E736974652E6463632E756368696C652E636C311C301A06092A864886F70D010901160D61646D696E4073756E73697465301E170D3135303531343137313634305A170D323530353131313731";
        ServerHello server =  new ServerHello(TLSUtil.hexStringToByteArray(response));
        System.out.println(server);
    }
}