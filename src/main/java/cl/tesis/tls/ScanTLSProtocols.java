package cl.tesis.tls;


import cl.tesis.mail.StartTLS;
import cl.tesis.mail.StartTLSProtocol;
import cl.tesis.mail.exception.ConnectionException;
import cl.tesis.tls.constant.TLSVersion;
import cl.tesis.tls.exception.SocketTLSHandshakeException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSConnectionException;
import cl.tesis.tls.exception.TLSHandshakeException;

import java.io.Closeable;
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
    private byte[] buffer;

    public ScanTLSProtocols(String host, int port) {
        this.host = host;
        this.port = port;
        this.buffer = new byte[BUFFER_SIZE];
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
        TLSHandshake tls = null;
        try {
             tls = new TLSHandshake(host, port, version);
             tls.connect();
        } catch (SocketTLSHandshakeException | TLSConnectionException e) {
            // TODO return null
            return false;
        } catch (TLSHandshakeException e) {
            return false;
        } finally {
            this.close(tls);
        }

        return version.getName().equals(tls.getProtocol());
    }

    private boolean scanProtocol(StartTLS start, TLSVersion version) {
        StartTLSProtocol startTLSProtocol = null;
        TLSHandshake tls = null;
        Socket socket = null;
        InputStream in = null;
        DataOutputStream out = null;

        try {
            startTLSProtocol = new StartTLSProtocol(host, port);
            startTLSProtocol.readBanner();

            tls =  new TLSHandshake(startTLSProtocol, start, version);
            tls.connect();

        } catch (SocketTLSHandshakeException | ConnectionException | StartTLSException | TLSConnectionException e) {
            // TODO return null
            return false;
        } catch (TLSHandshakeException e) {
            return false;
        }  finally {
            this.close(startTLSProtocol);
            this.close(tls);
        }


        return version.getName().equals(tls.getProtocol());
    }

    private void close(Closeable c) {
        if (c != null)
            try{
                c.close();
            } catch (IOException ignore) {
            }
    }

    public static void main(String[] args) {
        ScanTLSProtocols scanTLSProtocols =  new ScanTLSProtocols("192.80.24.4", 443);
        System.out.println(scanTLSProtocols.scanAllProtocols().toJson());

        scanTLSProtocols =  new ScanTLSProtocols("200.72.247.92", 25);
        System.out.println(scanTLSProtocols.scanAllProtocols(StartTLS.SMTP).toJson());

    }
}
