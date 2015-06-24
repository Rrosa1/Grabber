package cl.tesis;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.util.concurrent.*;


public class SSLConnection {
    private static final int DEFAULT_TIMEOUT = 120;
    private static final int MILLISECONDS = 1000;
    private static final int HANDSHAKE_TIMEOUT = 120;

    private SSLSocketFactory factory;
    private SSLSocket socket;
    private SSLSession session;
    private SocketAddress address;
    private int timeout;

    public SSLConnection(String host, int port, boolean validate) throws SSLConnectionException {
        this(host, port, validate, DEFAULT_TIMEOUT);
    }

    public SSLConnection(String host, int port, boolean validate, int timeout) throws SSLConnectionException {
        try {
            if (validate) {
                this.factory = HttpsURLConnection.getDefaultSSLSocketFactory();
            } else {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, TrustAllCert.getManager(), new java.security.SecureRandom());
                this.factory = sslContext.getSocketFactory();
            }
            this.socket = (SSLSocket) this.factory.createSocket();
            this.address = new InetSocketAddress(host, port);
            this.timeout = timeout;
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException  e) {
            throw new SSLConnectionException();
        }

    }

    public void connect() throws IOException, SSLConnectionException {
        if (this.timeout >= 0) {
            this.socket.connect(this.address, this.timeout * MILLISECONDS);
        } else {
            this.socket.connect(this.address);
        }

//        this.socket.startHandshake();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(new TimeoutHandshake(this.socket));

        try {
            future.get(HANDSHAKE_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true);
            executor.shutdownNow();
            throw new SSLConnectionException("HandShake Timeout" + this.address);
        }
        executor.shutdownNow();

        this.session = this.socket.getSession();
    }

    public void close() throws IOException {
        this.socket.close();
    }

    public Certificate getServerCertificate() throws SSLPeerUnverifiedException {
        return this.session.getPeerCertificates()[0];
    }

    class TimeoutHandshake implements Callable<Boolean> {
        private SSLSocket socket;

        public TimeoutHandshake(SSLSocket socket) {
            this.socket = socket;
        }

        @Override
        public Boolean call() throws Exception {
            this.socket.startHandshake();
            return true;
        }
    }

}