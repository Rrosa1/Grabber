package cl.tesis.input;

import cl.tesis.Certificate;
import cl.tesis.SSLConnection;
import cl.tesis.SSLConnectionException;

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class CSVFileReader implements  FileReader{
    private static final String CSV_SEPARATOR = ",";
    private String fileName;
    private BufferedReader reader;

    public CSVFileReader(String fileName) {
        this.fileName = fileName;
        try {
            this.reader = new BufferedReader(new java.io.FileReader(this.fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

    }

    @Override
    public synchronized String[] nextLine(){
        String line;

        try{
            line = this.reader.readLine();
        } catch (IOException error) {
            return null;
        }

        if (line != null){
            return line.split(CSV_SEPARATOR);
        }

        return null;
    }

    @Override
    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        CSVFileReader reader = new CSVFileReader("/home/eduardo/IdeaProjects/Grabber/src/cl/tesis/input/test.csv");
        String[] columns;

        while ((columns = reader.nextLine()) != null) {
            try {
                SSLConnection sslConnection = new SSLConnection(columns[0], 443, false, 10);
                sslConnection.connect();
                X509Certificate certificates = (X509Certificate) sslConnection.getServerCertificate();
                System.out.println(new Certificate(certificates, columns[0], false));
            } catch (SSLConnectionException e){
                System.out.println("Problem to create the socket");
            } catch (SSLHandshakeException e) {
                System.out.println("Invalid Certificate");
            } catch (SocketTimeoutException e) {
                System.out.println("TimeOut");
            } catch (ConnectException e) {
                System.out.println("Connect Exception");
            } catch (Exception e) {
                System.out.println("Exeption");
                e.printStackTrace();
            }

        }
    }

}
