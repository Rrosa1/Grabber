package cl.tesis.tls.handshake;

import cl.tesis.tls.TLSUtil;

public class ClientHello {
    private static final int RANDOM_SIZE = 32;
    public static String HEARTBEAT_MSG_TYPE = "16";
    public static String HEARTBEAT_HEADER = "00dc010000d8";
    public static String HEARTBEAT_BODY = "53435b909d9b720bbc0cbc2b92a84897cfbd3904cc160a8503909f770433d4de000066c014c00ac022c0210039003800880087c00fc00500350084c012c008c01cc01b00160013c00dc003000ac013c009c01fc01e00330032009a009900450044c00ec004002f00960041c011c007c00cc002000500040015001200090014001100080006000300ff01000049000b000403000102000a00340032000e000d0019000b000c00180009000a00160017000800060007001400150004000500120013000100020003000f0010001100230000000f000101";


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
    private String extension;

    public ClientHello(String TLSversion, TLSCipher cipherSuitesList) {
        this(TLSversion, cipherSuitesList, "");
    }

    public ClientHello(String TLSversion, TLSCipher cipherSuitesList, String extension) {
        this.extension = extension;
        this.extensionLength = TLSUtil.intToHex(TLSUtil.countBytes(this.extension), 2);
        this.compression = "00";
        this.compressionLength = "01";
        this.cipherSuites = cipherSuitesList.getValue();
        this.cipherSuitesLength = TLSUtil.intToHex(TLSUtil.countBytes(cipherSuites), 2);
        this.sessionIdLength = "00";
        this.random = TLSUtil.getRandomBytes(RANDOM_SIZE);
        this.clientVersion = TLSversion;

        this.handshakeBody = clientVersion + random + sessionIdLength + cipherSuitesLength + cipherSuites + compressionLength + compression + extensionLength + this.extension;
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

    public static byte[] heartbleedHello(TLSVersion version) {
        String packet = HEARTBEAT_MSG_TYPE + version.getStringVersion() + HEARTBEAT_HEADER + version.getStringVersion() + HEARTBEAT_BODY;
        return  TLSUtil.hexStringToByteArray(packet);
    }

}
