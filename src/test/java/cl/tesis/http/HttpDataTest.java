package cl.tesis.http;


import junit.framework.TestCase;

public class HttpDataTest extends TestCase{
    public HttpData data;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Http http =  new Http("192.80.24.4");
        data =  new HttpData("192.80.24.4");
        data.setHeader(http.getHeader());
        data.setIndex(http.getIndex());
    }

    public void testIP() throws Exception {
        assertEquals("192.80.24.4", data.getIp());
    }

    public void testResponseCode() throws Exception {
        assertEquals("HTTP/1.1 200 OK", data.getResponseCode());
    }

    public void testServer() throws Exception {
        assertEquals("Apache", data.getServer());
    }

    public void testDomain() throws Exception {
        assertEquals(null, data.getDomain());

    }
}
