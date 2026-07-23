package dev.donutquine.editor.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Cli cli = new Cli();
        CmdLineParser parser = new CmdLineParser(cli);

        try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
            LOGGER.error("Error occurred while parsing command-line arguments", e);
            return;
		}

        System.exit(cli.run());
    }
}
