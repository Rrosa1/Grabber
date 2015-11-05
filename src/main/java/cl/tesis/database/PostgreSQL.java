package cl.tesis.database;


import cl.tesis.tls.TLSUtil;

import javax.net.SocketFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PostgreSQL {
//    private static final String LOGIN_PACKET = "0000001100030001757365720070670000";
    private static final String LOGIN_PACKET = "000001000200005300000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000054000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "00000000000000000000000000000000000000000000000000000000000000000000000000";
    private static final int TIMEOUT = 60000;
    private static SocketFactory socketFactory = SocketFactory.getDefault();

    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;

    public PostgreSQL(String ip, int port) throws IOException {
        this.socket = socketFactory.createSocket(ip, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new DataOutputStream(this.socket.getOutputStream());
        socket.setSoTimeout(TIMEOUT);
    }

    public String getVersion() throws IOException {
        out.write(TLSUtil.hexStringToByteArray(LOGIN_PACKET));
        return this.in.readLine();
    }

    public void close() throws IOException {
        this.socket.close();
    }

    public static void main(String[] args) throws IOException {
        PostgreSQL postgreSQL = new PostgreSQL("85.128.247.214", 5432);
        System.out.printf(postgreSQL.getVersion());
    }
}
