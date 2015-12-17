package cl.tesis.tls;

import cl.tesis.mail.StartTLS;
import cl.tesis.tls.constant.CipherSuites;
import cl.tesis.tls.constant.TLSCipher;
import cl.tesis.tls.constant.TLSVersion;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;
import cl.tesis.tls.handshake.ClientHello;
import cl.tesis.tls.handshake.ServerHello;

import java.io.IOException;

public class ScanBeast extends Scan {

    public ScanBeast(String host, int port) {
        super(host, port);
    }

    public String hasBeast(){
        return this.hasBeast(null);
    }

    public String hasBeast(StartTLS startTLS) {
        byte[] ans = scanBeast(startTLS, TLSVersion.SSL_30);
        if (ans != null) {
            return CipherSuites.getNameByByte(ans);
        }

        ans = scanBeast(startTLS, TLSVersion.TLS_10);
        if (ans != null) {
            return CipherSuites.getNameByByte(ans);
        }
        return null;
    }

    private byte[] scanBeast(StartTLS startTLS, TLSVersion version) {
        ServerHello serverHello;
        try {
            this.getConnection(host, port);

            if (startTLS != null) {
                this.ignoreFirstLine();
                this.startProtocolHandshake(startTLS);
            }

            this.out.write(new ClientHello(version.getStringVersion(), TLSCipher.BEAST).toByte());
            this.in.read(buffer);

            serverHello = new ServerHello(buffer);
            return serverHello.getCipherSuite();
        } catch (StartTLSException | TLSHeaderException | HandshakeHeaderException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        ScanBeast s = new ScanBeast("172.17.68.37", 443);
//        s.hasBeast(null, TLSVersion.TLS_10);
    }
}