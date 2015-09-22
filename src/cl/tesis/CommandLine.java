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
    private String outputModule = "CSV";

    @Option(name = "-m", aliases = {"--module"}, usage = "Set the probe module", metaVar = "String", required = true, help = true)
    private String module;

    @Option(name = "-t", aliases = {"--threads"}, metaVar = "Int", usage = "Threads used to send probes", help = true)
    private int threads = 1;

    @Option(name = "--list-probe-modules", usage = "Print all probe modules", required = false, help = true)
    private boolean listProbeModules;

    @Option(name = "--list-output-modules", usage = "Print all output modules", required = false, help = true)
    private boolean listOutputModules;

    private String[] probeModules = {"SSLCertificate", "HTTPHeader", "SSHVersion"};
    private String[] outputModules = {"CSV", "JSON"};

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
        else if (listOutputModules) {
            System.out.println("Output Modules:");

            for (String m : outputModules)
                System.out.println(m);

            System.exit(0);
        }

    }

    private boolean runningArgs() {
        return getInput() == null || getOutput() == null || getModule() == null;
    }

    private boolean infoArgs() {
        return !(listProbeModules || listOutputModules);
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

    public static void main(String[] args) {
        new CommandLine().parse(args);
    }

}
