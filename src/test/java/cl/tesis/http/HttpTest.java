package cl.tesis.http;

import junit.framework.TestCase;

import java.util.List;
import java.util.Map;


public class HttpTest extends TestCase{
    public Http http;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        http = new Http("192.80.24.4");
    }

    public void testGetHeader() throws Exception {
        Map<String, List<String>> header =  http.getHeader();
        assertEquals(true, header.get("Server").contains("Apache"));
        assertEquals(true, header.get("Cache-Control").contains("must-revalidate"));
    }

    public void testIndex() throws Exception {
        String index =  http.getIndex();
        assertEquals(true, index.contains("content=\"http://www.dcc.uchile.cl/sites/default/files/imagenes/logos/logofb.jpg\""));
    }
}
