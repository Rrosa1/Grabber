package cl.tesis.dns;

import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DnsThreads extends  Thread{
    private static final Logger logger = Logger.getLogger(DnsThreads.class.getName());
    private static final int IP = 0;
    private static final int DNS_PORT = 53;
    private static final int TIMEOUT = 30000;

    private FileReader reader;
    private FileWriter writer;

    public DnsThreads(FileReader reader, FileWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        byte[] datagram = DefaultDnsDatagram.getDefaultDatagram();
        String[] columns;
        DatagramSocket socket;

        /* Create the socket */
        try {
            socket =  new DatagramSocket();
            socket.setSoTimeout(TIMEOUT);
        } catch (SocketException e) {
            logger.log(Level.INFO, "Problems creating the udp socket");
            e.printStackTrace();
            return;
        }

        while ((columns = this.reader.nextLine()) != null) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(datagram, datagram.length, InetAddress.getByName(columns[IP]), DNS_PORT),
                               receivePacket = new DatagramPacket(new byte[100], 100);
                socket.receive(receivePacket);
                System.out.println(new DnsDatagramParser(receivePacket.getAddress(), receivePacket.getPort(), receivePacket.getData()));
            } catch (SocketTimeoutException e) {
                logger.log(Level.INFO, "Read Timeout {0}", columns[IP]);
            } catch (UnknownHostException e) {
                logger.log(Level.INFO, "Unknown Host {0}", columns[IP]);
            } catch (IOException e) {
                logger.log(Level.INFO, "Socket Error {0}", columns[IP]);
            }

        }
    }
}
