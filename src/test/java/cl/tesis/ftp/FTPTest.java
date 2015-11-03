package cl.tesis.ftp;


import junit.framework.TestCase;

public class FTPTest extends TestCase{
    private FTP connection;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        connection = new FTP("66.39.21.217", 21);
    }

    public void testSSHVersion() throws Exception {
        assertEquals("220 qs3309.pair.com NcFTPd Server (licensed copy) ready.", connection.getVersion());
    }
}
