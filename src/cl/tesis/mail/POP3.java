package cl.tesis.mail;


import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class POP3 {

    public static void main(String[] args) throws IOException, StartTLSException, HandshakeHeaderException, TLSHeaderException {
        Socket socket =  new Socket("64.64.18.121", 110);
        InputStream in =  socket.getInputStream();
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        byte[] buf = new byte[4096];
        int readBytes = in.read(buf);
        System.out.println(new String(buf, 0, readBytes));

//        out.write("STLS\r\n".getBytes());
//        readBytes = in.read(buf);
//        System.out.println(new String(buf, 0, readBytes));

        TLS tls = new TLS(socket);
        tls.doMailHandshake(StartTLS.POP3);
    }

}
