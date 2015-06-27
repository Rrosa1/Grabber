package cl.tesis;

import cl.tesis.exception.SSLConnectionException;
import cl.tesis.exception.SSLHandshakeTimeoutException;
import cl.tesis.input.FileReader;

import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;


public class ConnectionThreads extends  Thread{
    private FileReader reader;
    public ConnectionThreads(FileReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String[] columns;

        while ((columns = this.reader.nextLine()) != null) {
            try {
                Certificate certificate = SSLUtil.getServerCertificate(columns[0], false);
                System.out.println(certificate.toString());
            } catch (SSLHandshakeException e) { // Untrusted Certificate
                e.printStackTrace();
                System.out.println("HandShake " + columns[0]);
            } catch (SSLConnectionException e) { // Problem creating the socket
                e.printStackTrace();
                System.out.println("Problem to create the socket " + e.getMessage());
            } catch (SocketTimeoutException | SSLHandshakeTimeoutException e) { // Timeout
                e.printStackTrace();
                System.out.println("TimeOut " + columns[0]);
            } catch (ConnectException e) { // Problem in the connection
                e.printStackTrace();
                System.out.println("Connect Exception " + columns[0]);
            } catch (Throwable e) { // Other errors
                e.printStackTrace();
                System.out.println("Exception " + columns[0]);
            }

        }
    }


}
