package cl.tesis;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.Messages;

public class CommandLine {

    @Option(name = "-i", aliases = {"--input"}, usage = "Input file", metaVar = "File" , required = true, help = true)
    private String input;

    @Option(name = "-o", aliases = {"--output"}, usage = "Output file", metaVar = "File", required = true, help = true)
    private String output;

    @Option(name = "-om", aliases = {"--outputModule"}, usage = "Output file format", metaVar = "String", required = false, help = true)
    private String outputModule = "JSON";

    @Option(name = "-m", aliases = {"--module"}, usage = "Set the probe module", metaVar = "String", required = true, help = true)
    private String module;

    @Option(name = "-t", aliases = {"--threads"}, metaVar = "Int", usage = "Threads used to send probes", help = true)
    private int threads = 1;

    @Option(name = "-p", aliases = {"--port"}, metaVar = "Int", usage = "Port to scan", required = true, help = true)
    private int port;

    @Option(name = "--startTLS", usage =  "Start a TLS connection in non secure port", required = false, help = true)
    private boolean startTLS;

    @Option(name = "--allTLSProtocols", usage = "Test SSLv3.0, TLSv1.0, TLSv1.1, TLSv1.2", help =  true)
    private boolean allTLSProtocols;

    @Option(name = "--allCipherSuites", usage = "Test 10 groups of cipher suites", help =  true)
    private boolean allCipherSuites;

    @Option(name = "--heartbleed", usage = "Test heartbleed", help =  true)
    private boolean heartbleed;

    @Option(name = "--beast", usage = "Test beast", help =  true)
    private boolean beast;


    @Option(name = "--list-probe-modules", usage = "Print all probe modules", required = false, help = true)
    private boolean listProbeModules;

    @Option(name = "--list-output-modules", usage = "Print all output modules", required = false, help = true)
    private boolean listOutputModules;

    @Option(name= "--version", usage = "Print version and exit", required = false, help = true)
    private boolean version;

    private String[] probeModules = {"HTTPSCertificate", "HTTP", "HTTPS", "SSHVersion", "SMTP", "POP3", "IMAP", "FTP"};
    private String[] outputModules = {"CSV", "JSON"};
    private String versionDetails = "v1.2";

    public void parse(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            this.required(parser);

        } catch (CmdLineException e) {
            parser.printUsage(System.out);
            System.exit(0);
        }

        if (listProbeModules) {
            System.out.println("Probe Modules:");

            for (String m : probeModules)
                System.out.println(m);

            System.exit(0);
        }
        if (listOutputModules) {
            System.out.println("Output Modules:");

            for (String m : outputModules)
                System.out.println(m);

            System.exit(0);
        }
        if (version) {
            System.out.println("version: " + versionDetails);
            System.exit(0);
        }

    }

    private boolean runningArgs() {
        return getInput() == null || getOutput() == null || getModule() == null || getPort() == 0;
    }

    private boolean infoArgs() {
        return !(listProbeModules || listOutputModules || version);
    }

    public void required(CmdLineParser parser) throws CmdLineException {
        if (runningArgs() && infoArgs()) {
            throw new CmdLineException(parser, Messages.FORMAT_ERROR_FOR_MAP, "Require options");
        }
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public String getOutputModule() {
        return outputModule;
    }

    public int getThreads() {
        return threads;
    }

    public String getModule() {
        return module;
    }

    public int getPort() {
        return port;
    }

    public boolean isStartTLS() {
        return startTLS;
    }

    public boolean isAllTLSProtocols() {
        return allTLSProtocols;
    }

    public boolean isAllCipherSuites() {
        return allCipherSuites;
    }

    public boolean isHeartbleed() {
        return heartbleed;
    }

    public boolean isBeast() {
        return beast;
    }

    public static void main(String[] args) {
        new CommandLine().parse(args);
    }

}
