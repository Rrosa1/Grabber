package cl.tesis.ftp;


import javax.net.SocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class FTP {
    private static final int TIMEOUT = 60000;
    private static SocketFactory socketFactory = SocketFactory.getDefault();

    private Socket socket;
    private BufferedReader in;

    public FTP(String ip, int port) throws IOException {
        this.socket = socketFactory.createSocket(ip, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socket.setSoTimeout(TIMEOUT);
    }

    public String getVersion() throws IOException {
        return this.in.readLine();
    }

    public void close() throws IOException {
        this.socket.close();
    }
}
