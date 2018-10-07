package nelson.boofcv.panocube;

import org.kohsuke.args4j.Option;

import java.io.File;

public class Params {

    @Option(name = "-i", required = true)
    File input;

    @Option(name = "-o", required = true)
    File output;

    @Option(name = "-n", required = true)
    String name;

    @Option(name = "-s", required = false)
    int size = -1;
}
