package cl.tesis.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class PacketParserScript {
    private static final int IN = 0;
    private static final int OUT = 1;
    private static final String BEGIN = "BEGIN";
    private static final String END = "END";

    private BufferedReader reader;

    public PacketParserScript(BufferedReader bufferedReader) {
        this.reader = bufferedReader;
    }

    public ArrayList<String> parse() {
        ArrayList<String> packets = new ArrayList<>();
        int status = OUT;
        String currentLine, currentPacket = "";

        try {
            while ((currentLine = reader.readLine()) != null) {
                currentLine =  cleanString(currentLine);

                if (currentLine.equals(BEGIN)){
                    status = IN;
                } else if (currentLine.equals(END)){
                    status = OUT;
                    packets.add(currentPacket);
                    currentPacket = "";
                } else if (status == IN){
                    currentPacket += currentLine;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return packets;
    }

    private static String cleanString(String text){
        return text.replaceAll("\r", "").replaceAll("\n", "").trim();
    }
}
