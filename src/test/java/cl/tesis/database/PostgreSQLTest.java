package cl.tesis.database;


import junit.framework.TestCase;

public class PostgreSQLTest extends TestCase{
    public PostgreSQL postgreSQL;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        postgreSQL = new PostgreSQL("85.128.247.214", 5432);
    }

    public void testVersion() throws Exception {
        String response =  postgreSQL.getResponse();
        assertEquals(true, response.contains("unsupported frontend protocol 512.83: server supports 1.0 to 3.0"));
    }
}
