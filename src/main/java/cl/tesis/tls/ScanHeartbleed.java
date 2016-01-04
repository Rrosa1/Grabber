package cl.tesis.tls;


import cl.tesis.mail.StartTLS;
import cl.tesis.tls.constant.TLSVersion;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;
import cl.tesis.tls.handshake.ClientHello;
import cl.tesis.tls.handshake.ServerHello;
import cl.tesis.tls.util.TLSUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class ScanHeartbleed extends Scan{


    public ScanHeartbleed(String host, int port) {
        super(host, port);
    }

    public HeartbleedData hasHeartbleed() {
        return this.scanHeartbeat(null, TLSVersion.TLS_12);
    }

    public HeartbleedData hasHeartbleed(StartTLS start) {
        return this.scanHeartbeat(start, TLSVersion.TLS_12);
    }

    private HeartbleedData scanHeartbeat(StartTLS start, TLSVersion version) {
        HeartbleedData data = new HeartbleedData();
        ServerHello serverHello;
        try {
            this.getConnection(host, port);

            if (start != null) {
                this.ignoreFirstLine();
                this.startProtocolHandshake(start);
            }

            this.out.write(ClientHello.heartbleedHello(version));
            this.in.read(buffer);

            serverHello = new ServerHello(buffer);
            data.setHeartbeat(serverHello.hasHeartbeat());

            this.readAll();

            // Heartblead packet
            this.out.write(TLSUtil.hexStringToByteArray(heartbleedPacket(version)));
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {}
            int a = this.in.read(buffer);
            if(a > 0 ){
                data.setHeartbleed(true);
            }
        } catch (StartTLSException | TLSHeaderException | HandshakeHeaderException | IOException e) {
            return data;
        } finally {
            this.close();
        }
        return data;
    }

    private void readAll(){
        try {
            while (true){
                int length = this.in.read(buffer);
                if (length <= 0)
                    break;
            }
        } catch (SocketTimeoutException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }


    private String heartbleedPacket(TLSVersion tlsVersion){
        return "18" + tlsVersion.getStringVersion() + "0003014000";
    }

}
