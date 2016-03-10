package cl.tesis.script;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ParserPacketTest extends TestCase{

    public void testName() throws Exception {
        assertNotNull("Test file missing",getClass().getResource("/one_packet.txt"));
    }

    public void testReadOnePacketWithoutNewLine() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/one_packet.txt")));
        PacketParserScript packetParser = new PacketParserScript(bufferedReader);
        ArrayList<String> packets = packetParser.parse();
        assertEquals("515745525459", packets.get(0));
    }

    public void testReadOnePacketWithNewLine() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/one_packet_newline.txt")));
        PacketParserScript packetParser = new PacketParserScript(bufferedReader);
        ArrayList<String> packets = packetParser.parse();
        assertEquals("515745525459", packets.get(0));
    }

    public void testReadManyPacketsWithoutNewLine() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/many_packets.txt")));
        PacketParserScript packetParser = new PacketParserScript(bufferedReader);
        ArrayList<String> packets = packetParser.parse();
        assertEquals("515745525459", packets.get(0));
        assertEquals("515754594949", packets.get(1));
    }

    public void testReadManyPacketsWithNewLine() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/many_packets.txt")));
        PacketParserScript packetParser = new PacketParserScript(bufferedReader);
        ArrayList<String> packets = packetParser.parse();
        assertEquals("515745525459", packets.get(0));
        assertEquals("515754594949", packets.get(1));
    }

    public void testReadNewLineBetweenPackets() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/newline_between.txt")));
        PacketParserScript packetParser = new PacketParserScript(bufferedReader);
        ArrayList<String> packets = packetParser.parse();
        assertEquals("515745525459", packets.get(0));
        assertEquals("515754594949", packets.get(1));
    }

    public void testFull() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/full_packets.txt")));
        PacketParserScript packetParser = new PacketParserScript(bufferedReader);
        ArrayList<String> packets = packetParser.parse();
        assertEquals("515745525459", packets.get(0));
        assertEquals("515754594949", packets.get(1));
        assertEquals("86954594949", packets.get(2));
    }






}
