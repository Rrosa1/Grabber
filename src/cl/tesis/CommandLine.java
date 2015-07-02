package cl.tesis;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class CommandLine {

    @Option(name = "-i", aliases = {"--input"}, metaVar = "File",usage = "Input file", help = true, required = true)
    private String input = "";

    @Option(name = "-o", aliases = {"--output"}, metaVar = "File", usage = "Output file", help = true, required = true)
    private String output = "";

    @Option(name = "-t", aliases = {"--threads"}, metaVar = "Int", usage = "Number of threads", help = true)
    private int threads = 1;

    public void parse(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);

        } catch (CmdLineException e) {
            parser.printUsage(System.out);
            System.exit(0);
        }

    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public int getThreads() {
        return threads;
    }

}
