package cl.tesis.http;

import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

public class HttpStressTest extends TestCase {

    public static void assertString(String expected, String actual) {
        assertEquals(true, actual.contains(expected));
    }

    public void testIP1() throws Exception {
        Http http =  new Http("190.121.119.253", 80);
        Map<String, List<String>> header =  http.getHeader();
        assertEquals("[HTTP/1.1 401 N/A]", header.get(null).toString());
        assertEquals("[TP-LINK Router]", header.get("Server").toString());
    }

    public void testIP2() throws  Exception {
        Http http = new Http("191.101.17.218", 80);
        Map<String, List<String>> header =  http.getHeader();
        assertEquals("[HTTP/1.0 302 Moved Temporarily]", header.get(null).toString());
        assertEquals("[squid/3.1.20]", header.get("Server").toString());
    }
}
