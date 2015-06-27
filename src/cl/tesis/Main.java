package cl.tesis;

import cl.tesis.exception.SSLConnectionException;
import cl.tesis.input.CSVFileReader;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, KeyManagementException, SSLConnectionException, InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        String fileName = "/home/eduardo/IdeaProjects/Grabber/src/cl/tesis/input/test.csv";
        CSVFileReader reader = new CSVFileReader(fileName);
        List<Thread> lista = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Thread t = new ConnectionThreads(reader);
            t.start();
            lista.add(t);
        }

        for (Thread thread : lista) {
            thread.join();
        }

        Date date1 = new Date();
        System.out.println(dateFormat.format(date1));

    }
}