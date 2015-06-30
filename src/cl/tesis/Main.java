package cl.tesis;

import cl.tesis.input.CSVFileReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException, IOException {
        LogManager.getLogManager().readConfiguration(new FileInputStream("src/cl/tesis/logger.properties"));
        logger.info("Start Scanning" );

        String fileName = "src/cl/tesis/input/test.csv";
        CSVFileReader reader = new CSVFileReader(fileName);
        List<Thread> lista = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread t = new ConnectionThreads(reader);
            t.start();
            lista.add(t);
        }

        for (Thread thread : lista) {
            thread.join();
        }

       logger.info("End Scanning");

    }
}