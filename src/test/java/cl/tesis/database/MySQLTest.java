package cl.tesis.database;


import junit.framework.TestCase;

public class MySQLTest extends TestCase{
    public MySQL mySQL;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mySQL = new MySQL("206.130.107.250", 3306);
    }

    public void testVersion() throws Exception {
        assertEquals(true, mySQL.getVersion().contains("is not allowed to connect to this MySQL server"));
    }
}
