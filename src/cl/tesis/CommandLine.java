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

    @Option(name = "-m", aliases = {"--module"}, usage = "Set the probe module", metaVar = "String", required = true, help = true)
    private String module;

    @Option(name = "-t", aliases = {"--threads"}, metaVar = "Int", usage = "Threads used to send probes", help = true)
    private int threads = 1;

    @Option(name = "--list-probe-modules", usage = "Print all probe modules", required = false, help = true)
    private boolean listProbeModules;

    private String[] probeModule = {"SSLCertificate", "UDP_DNS"};

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public int getThreads() { return threads; }

    public String getModule() {
        return module;
    }

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

            for (String m : probeModule)
                System.out.println(m);

            System.exit(0);
        }

    }

    private boolean runningArgs() {
        return getInput() == null || getOutput() == null || getModule() == null;
    }

    private boolean infoArgs() {
        return !listProbeModules;
    }

    public void required(CmdLineParser parser) throws CmdLineException {
        if (runningArgs() && infoArgs()) {
            throw new CmdLineException(parser, Messages.FORMAT_ERROR_FOR_MAP, "Require options");
        }
    }

    public static void main(String[] args) {
        new CommandLine().parse(args);
    }

}
