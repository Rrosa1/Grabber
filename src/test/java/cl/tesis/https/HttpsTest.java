package cl.tesis.https;

import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

public class HttpsTest extends TestCase{
    public Https https;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        https = new Https("192.80.24.4", 443);
    }

    public void testHeader() throws Exception {
        Map<String, List<String>> header =  https.getHeader();
        assertEquals(true, header.get("Server").contains("Apache"));
        assertEquals(true, header.get("Keep-Alive").contains("timeout=15, max=100"));
    }

    public void testIndex() throws Exception {
        String index =  https.getIndex();
        assertEquals(true, index.contains("action=\"https://intranet.dcc.uchile.cl/ingreso/validar_ingreso.php\""));
    }
}
