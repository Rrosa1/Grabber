package tlsNew;


import cl.tesis.mail.StartTLS;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;
import cl.tesis.tls.handshake.ClientHello;
import cl.tesis.tls.handshake.ServerHello;
import cl.tesis.tls.handshake.TLSVersion;

import java.io.IOException;

public class ScanHeartbleed extends Scan{


    public ScanHeartbleed(String host, int port) {
        super(host, port);
    }

    public HeartbleedData hasHeartbleed() {
        return new HeartbleedData(this.scanHeartbeat(null, TLSVersion.TLS_12));
    }

    public HeartbleedData hasHeartbleed(StartTLS start) {
        return new HeartbleedData(this.scanHeartbeat(start, TLSVersion.TLS_12));
    }

    private boolean scanHeartbeat(StartTLS start, TLSVersion version) {
        ServerHello serverHello;
        try {
            this.getConnection(host, port);

            if (start != null) {
                this.ignoreFirstLine();
                this.startProtocolHandshake(start);
            }

            this.out.write(ClientHello.heartbleedHello(version));
            this.in.read(buffer);

            serverHello = new ServerHello(buffer);
        } catch (StartTLSException | TLSHeaderException | HandshakeHeaderException | IOException e) {
            return false;
        }
        return serverHello.hasHeartbeat();
    }
}
