package cl.tesis.ftp;


import cl.tesis.mail.StartTLSProtocol;
import cl.tesis.mail.exception.ConnectionException;

import java.io.IOException;

public class FTP  extends StartTLSProtocol{
    private static final int DEFAULT_PORT = 21;

    public FTP(String host, int port) throws ConnectionException {
        super(host, port);
    }

    public FTP(String host) throws ConnectionException {
        this(host, DEFAULT_PORT);
    }

    public String getVersion() throws IOException {
        String ret;
        try {
            int readBytes = in.read(buffer);
            if (readBytes <= 0)
                return null;

            ret = new String(buffer, 0, readBytes);
        } catch (IOException e) {
            return null;
        }

        return ret;
    }

}
