package cl.tesis.ftp;


import junit.framework.TestCase;

public class FTPTest extends TestCase{
    private FTP ftp;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        ftp = new FTP("54.208.159.183", 21);
    }

    public void testSSHVersion() throws Exception {
        assertEquals(true, ftp.getVersion().contains("220---------- Welcome to Pure-FTPd [privsep] [TLS] ----------"));
    }

}
