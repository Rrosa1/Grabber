package tlsNew;


import cl.tesis.mail.StartTLS;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.handshake.TLSVersion;
import tlsNew.exception.SocketTLSHandshakeException;
import tlsNew.exception.TLSConnectionException;
import tlsNew.exception.TLSHandshakeException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ScanTLSProtocols {
    private static final int MILLISECONDS = 1000;
    private static final int TIMEOUT = 60 * MILLISECONDS; // 60 seg
    private static final int BUFFER_SIZE = 2048;
    private String host;
    private int port;

    public ScanTLSProtocols(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ScanTLSProtocolsData scanAllProtocols() {
        ScanTLSProtocolsData scanTLSProtocolsData = new ScanTLSProtocolsData();

        for (TLSVersion tlsVersion : TLSVersion.values()) {
            scanTLSProtocolsData.setTLSVersion(tlsVersion, scanProtocol(tlsVersion));
        }

        return scanTLSProtocolsData;
    }

    public ScanTLSProtocolsData scanAllProtocols(StartTLS start) {
        ScanTLSProtocolsData scanTLSProtocolsData = new ScanTLSProtocolsData();

        for (TLSVersion tlsVersion : TLSVersion.values()) {
            scanTLSProtocolsData.setTLSVersion(tlsVersion, scanProtocol(start, tlsVersion));
        }

        return scanTLSProtocolsData;
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

    private boolean scanProtocol(StartTLS start, TLSVersion version) {
        TLSHandshake tls;
        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(TIMEOUT);
            InputStream in = socket.getInputStream();
            DataOutputStream out =  new DataOutputStream(socket.getOutputStream());
            byte[] buffer = new byte[BUFFER_SIZE];

            in.read(buffer);
            tls =  new TLSHandshake(socket, start, version);
            tls.connect();

        } catch (SocketTLSHandshakeException | StartTLSException | TLSConnectionException | IOException e) {
            System.out.println(e.getMessage());
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
