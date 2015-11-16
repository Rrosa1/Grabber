package cl.tesis.https.handshake;


import cl.tesis.mail.StartTLS;
import cl.tesis.tls.ScanTLSVersion;
import cl.tesis.tls.handshake.TLSVersion;

public class ScanTLSProtocols {
    private String host;
    private int port;

    // SMTP, POP3, IMAP
    private StartTLS start;

    public ScanTLSProtocols(String host, int port, StartTLS start) {
        this.host = host;
        this.port = port;
        this.start = start;
    }

    public ScanTLSProtocols(String host, int port) {
        this(host, port, null);
    }

    public ScanTLSVersion scanAllProtocols() {
        ScanTLSVersion scanTLSVersion = new ScanTLSVersion();

        for (TLSVersion tlsVersion : TLSVersion.values()) {
            scanTLSVersion.setTLSVersion(tlsVersion, scanProtocol(tlsVersion));
        }

        return scanTLSVersion;
    }

    private boolean scanProtocol(TLSVersion version) {
        TLSHandshake tls;
        try {
             tls = new TLSHandshake(host, port, version);
             tls.connect();
        } catch (SocketTLSHandshakeException | TLSConnectionException e) {
            // TODO return null
            return false;
        } catch (TLSHandshakeException e) {
            return false;
        }

        return version.getName().equals(tls.getProtocol());
    }

    public static void main(String[] args) {
        ScanTLSProtocols scanTLSProtocols =  new ScanTLSProtocols("192.80.24.4", 443);
        System.out.println(scanTLSProtocols.scanAllProtocols().toJson());

    }
}
