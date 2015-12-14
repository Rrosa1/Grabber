package cl.tesis.tls.handshake;


import cl.tesis.tls.constant.ExtensionType;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.TLSHeaderException;
import cl.tesis.tls.util.TLSUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerHello {

    private static final int TLS_HEADER_LENGTH = 5;
    private static final int HANDSHAKE_HEADER_LENGTH = 4;
    private static final int RANDOM_LENGTH = 32;
    private static final int SESSION_ID_START = RANDOM_LENGTH + 3;
    private static final int CIPHER_SUITE_START = SESSION_ID_START;
    private static final int COMPRESSION_METHOD_START = CIPHER_SUITE_START + 2;
    private static final int EXTENSION_LENGTH_START = COMPRESSION_METHOD_START + 1;
    private static final int EXTENSION_START = EXTENSION_LENGTH_START + 2;

    private byte[] tlsHeader;
    private byte[] handshakeHeader;
    private byte[] handshakeBody;
    private byte[] protocolVersion;
    private byte[] random;
    private byte[] sessionID;
    private byte[] cipherSuite;
    private byte compressionMethod;
    private int extensionLength;
    private byte[] extensions;
    private ArrayList<String> extensionsNames;


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
        if (TLS_HEADER_LENGTH + HANDSHAKE_HEADER_LENGTH >= tlsBodyLength + TLS_HEADER_LENGTH)
            throw new HandshakeHeaderException("Error in Server Hello header");
        this.handshakeBody = Arrays.copyOfRange(array, TLS_HEADER_LENGTH + HANDSHAKE_HEADER_LENGTH, tlsBodyLength + TLS_HEADER_LENGTH);

        /* Parsing handshake body */
        this.protocolVersion = new byte[]{this.handshakeBody[0], this.handshakeBody[1]};
        this.random = Arrays.copyOfRange(this.handshakeBody, 2, RANDOM_LENGTH + 2);
        int sessionIdLenght = this.handshakeBody[34];
        this.sessionID = Arrays.copyOfRange(this.handshakeBody, SESSION_ID_START, SESSION_ID_START + sessionIdLenght);
        this.cipherSuite = new byte[]{this.handshakeBody[CIPHER_SUITE_START + sessionIdLenght], this.handshakeBody[CIPHER_SUITE_START + sessionIdLenght + 1]};
        this.compressionMethod = this.handshakeBody[COMPRESSION_METHOD_START + sessionIdLenght];

        /* Parsing Extensions*/
        this.extensions = null;
        this.extensionsNames = new ArrayList<>();
        if (EXTENSION_LENGTH_START + sessionIdLenght < this.handshakeBody.length){
            extensionLength =  TLSUtil.bytesToInt(this.handshakeBody[EXTENSION_LENGTH_START + sessionIdLenght], this.handshakeBody[EXTENSION_LENGTH_START + sessionIdLenght + 1]);
            this.extensions = Arrays.copyOfRange(this.handshakeBody, EXTENSION_START + sessionIdLenght, EXTENSION_START + sessionIdLenght + extensionLength);

            for (int i = 0; i < extensionLength ;) {
                byte[] extensionType =  new byte[] {extensions[i], extensions[i+1]};
                int extensionDataLenght = TLSUtil.bytesToInt(extensions[i+2], extensions[i+3]);
//                if (extensionDataLenght == 0){
//                    break;
//                }
                extensionsNames.add(ExtensionType.getNameByByte(extensionType));
                i += extensionDataLenght + 4;
            }

        }

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

    public boolean hasHeartbeat() {
        return this.extensionsNames.contains(ExtensionType.HEARTBEAT.getName());
    }

    public byte[] getProtocolVersion() {
        return protocolVersion;
    }

    public byte[] getCipherSuite() {
        return cipherSuite;
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
                ", CompressionMethod=" + String.format("%02x", compressionMethod) +
                ", Extension Length=" + extensionLength +
                ", Extensions=" + TLSUtil.bytesToHex(extensions) +
                ", ExtensionsNames=" + Arrays.toString(extensionsNames.toArray(new String[extensionsNames.size()])) +
                '}';
    }
}