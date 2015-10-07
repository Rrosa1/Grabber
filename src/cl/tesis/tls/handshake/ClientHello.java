package cl.tesis.tls.handshake;

import cl.tesis.tls.TLSUtil;

public class ClientHello {
    private static final int RANDOM_SIZE = 32;

    private String tlsHeader;
    private String tlsBody;
    private String handshakeHeader;
    private String handshakeBody;
    private String clientVersion;
    private String random;
    private String sessionIdLength;
    private String cipherSuitesLength;
    private String cipherSuites;
    private String compressionLength;
    private String compression;
    private String extensionLength;

    public ClientHello(String TLSversion, String cipherSuitesList) {
        this.extensionLength = "0000";
        this.compression = "00";
        this.compressionLength = "01";
        this.cipherSuites = cipherSuitesList;
        this.cipherSuitesLength = TLSUtil.intToHex(TLSUtil.countBytes(cipherSuites), 2);
        this.sessionIdLength = "00";
        this.random = TLSUtil.getRandomBytes(RANDOM_SIZE);
        this.clientVersion = TLSversion;

        this.handshakeBody = clientVersion + random + sessionIdLength + cipherSuitesLength + cipherSuites + compressionLength + compression + extensionLength;
        this.handshakeHeader = HandshakeType.CLIENT_HELLO + TLSUtil.intToHex(TLSUtil.countBytes(handshakeBody), 3);

        this.tlsBody = handshakeHeader + handshakeBody;
        this.tlsHeader = "16" + TLSversion + TLSUtil.intToHex(TLSUtil.countBytes(tlsBody), 2);
    }

    public byte[] toByte() {
        return TLSUtil.hexStringToByteArray(this.tlsHeader + this.tlsBody);
    }

    @Override
    public String toString() {
        return "ClientHello{" +
                "tlsHeader='" + tlsHeader + '\'' +
                ", handshakeHeader='" + handshakeHeader + '\'' +
                ", clientVersion='" + clientVersion + '\'' +
                ", random='" + random + '\'' +
                ", sessionIdLength='" + sessionIdLength + '\'' +
                ", cipherSuitesLength='" + cipherSuitesLength + '\'' +
                ", cipherSuites='" + cipherSuites + '\'' +
                ", compressionLength='" + compressionLength + '\'' +
                ", compression='" + compression + '\'' +
                ", extensionLength='" + extensionLength + '\'' +
                '}';
    }

}
