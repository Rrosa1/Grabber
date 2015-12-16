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

    public void hasBeast(StartTLS startTLS, TLSVersion version) {
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
            System.out.println(CipherSuites.getNameByByte(serverHello.getCipherSuite()));
        } catch (StartTLSException | TLSHeaderException | HandshakeHeaderException | IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ScanBeast s = new ScanBeast("172.17.68.37", 443);
        s.hasBeast(null, TLSVersion.TLS_10);
    }
}