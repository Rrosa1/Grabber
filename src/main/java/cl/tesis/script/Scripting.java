package cl.tesis.script;

import cl.tesis.mail.exception.ConnectionException;
import cl.tesis.tls.util.TLSUtil;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Scripting implements Closeable{

    private static final int MILLISECONDS = 1000;
    private static final int TIMEOUT = 30 * MILLISECONDS; // 30 Seg
    private static final int BUFFER_SIZE = 2048;

    private String host;
    private Socket socket;
    private ArrayList<String> packets;
    private InputStream in;
    private DataOutputStream out;
    private byte[] buffer;

    public Scripting(String host, int port, ArrayList<String> packets) throws ConnectionException {
        try {
            this.host = host;
            this.packets = packets;
            this.socket = new Socket(host, port);
            this.socket.setSoTimeout(TIMEOUT);

            this.in = socket.getInputStream();
            this.out = new DataOutputStream(socket.getOutputStream());
            this.buffer = new byte[BUFFER_SIZE];

        } catch (IOException e) {
            throw new ConnectionException("Can't create the connection");
        }
    }

    public String sendPacket() {
        String ret;
        try {
            this.out.write(TLSUtil.hexStringToByteArray(this.packets.get(0)));

            int readBytes = in.read(buffer);
            if (readBytes <= 0)
                return null;

            ret = TLSUtil.byteArrayToHexString(buffer, 0, readBytes);
        } catch (IOException e) {
            return null;
        }
        return ret;
    }

    @Override
    public void close() throws IOException {
        if (in != null)
            try{
                in.close();
            } catch (IOException ignore) {
            } finally { in = null; }

        if (out != null)
            try{
                out.close();
            } catch (IOException ignore) {
            } finally { out = null; }

        if (socket != null)
            try{
                socket.close();
            } catch (IOException ignore) {
            } finally { socket = null; }
    }
}
