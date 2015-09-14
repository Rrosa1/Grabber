package cl.tesis.ssh;

import javax.net.SocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SSHConnection {

    private static final int TIMEOUT = 60000;
    private static SocketFactory socketFactory = SocketFactory.getDefault();

    private Socket socket;
    private BufferedReader in;

    public SSHConnection(String host, int port) throws IOException {
        this.socket = socketFactory.createSocket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socket.setSoTimeout(TIMEOUT);
    }

    public String getSSHVersion() throws IOException {
        return this.in.readLine();
    }
}
