package cl.tesis;

import cl.tesis.http.HttpThread;
import cl.tesis.https.HttpsCertificateThread;
import cl.tesis.https.HttpsThread;
import cl.tesis.input.CSVFileReader;
import cl.tesis.mail.IMAPThread;
import cl.tesis.mail.POP3Thread;
import cl.tesis.mail.SMTPThread;
import cl.tesis.output.FileWriter;
import cl.tesis.output.FileWriterFactory;
import cl.tesis.ssh.SshThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException, IOException {
        LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logger.properties"));

        // Parse the command arguments
        CommandLine commandLine = new CommandLine();
        commandLine.parse(args);

        // Define Output Factory
        FileWriterFactory writerFactory = new FileWriterFactory();

        logger.info("Start Scanning");

        try (CSVFileReader reader = new CSVFileReader(commandLine.getInput());
             FileWriter writer = writerFactory.getFileWriter(commandLine.getOutputModule(), commandLine.getOutput())) {

            List<Thread> lista = new ArrayList<>();

            switch (commandLine.getModule()){
                case "HTTPS":
                    for (int i = 0; i < commandLine.getThreads(); i++) {
                        Thread t = new HttpsThread(reader, writer, commandLine.getPort());
                        t.start();
                        lista.add(t);
                    }
                    break;
                case "HTTPSCertificate":
                    for (int i = 0; i < commandLine.getThreads(); i++) {
                        Thread t = new HttpsCertificateThread(reader, writer, commandLine.getPort(), commandLine.isAllTLSProtocols(), commandLine.isAllCipherSuites(), commandLine.isHeartbleed());
                        t.start();
                        lista.add(t);
                    }
                    break;
                case "HTTP":
                    for (int i = 0; i < commandLine.getThreads(); i++) {
                        Thread t = new HttpThread(reader, writer, commandLine.getPort());
                        t.start();
                        lista.add(t);
                    }
                    break;
                case "SSHVersion":
                    for (int i = 0; i < commandLine.getThreads(); i++) {
                        Thread t = new SshThread(reader, writer);
                        t.start();
                        lista.add(t);
                    }
                    break;
                case "SMTP":
                    for (int i = 0; i < commandLine.getThreads(); i++) {
                        Thread t = new SMTPThread(reader, writer, commandLine.getPort(), commandLine.isStartTLS(), commandLine.isAllTLSProtocols(), commandLine.isAllCipherSuites(), commandLine.isHeartbleed());
                        t.start();
                        lista.add(t);
                    }
                    break;
                case "POP3":
                    for (int i = 0; i < commandLine.getThreads(); i++) {
                        Thread t = new POP3Thread(reader, writer, commandLine.getPort(), commandLine.isStartTLS(), commandLine.isAllTLSProtocols(), commandLine.isAllCipherSuites(), commandLine.isHeartbleed());
                        t.start();
                        lista.add(t);
                    }
                    break;
                case "IMAP":
                    for (int i = 0; i < commandLine.getThreads(); i++) {
                        Thread t = new IMAPThread(reader, writer, commandLine.getPort(), commandLine.isStartTLS(), commandLine.isAllTLSProtocols(), commandLine.isAllCipherSuites(), commandLine.isHeartbleed());
                        t.start();
                        lista.add(t);
                    }
                    break;
                default:
                    logger.info("Probe module not found");
                    System.exit(0);
            }

            for (Thread thread : lista) {
                thread.join();
            }

        } catch (IOException e) {
            logger.info("Problems to close read or write files");
        }

        logger.info("End Scanning");

    }
}