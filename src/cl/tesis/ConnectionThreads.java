package cl.tesis;

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
            } catch (SSLConnectionException e){
                System.out.println("Problem to create the socket");
            } catch (SSLHandshakeException e) {
                System.out.println("HandShake");
            } catch (SocketTimeoutException e) {
                System.out.println("TimeOut");
            } catch (ConnectException e) {
                System.out.println("Connect Exception");
            } catch (Exception e) {
                System.out.println("Exception");
            }

        }
    }


}
