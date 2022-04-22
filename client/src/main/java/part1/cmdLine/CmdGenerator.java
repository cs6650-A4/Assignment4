package part1.cmdLine;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * The CmdGenerator is to generate system command-line options,some of which are required and some
 * of which are optional.
 */
public class CmdGenerator {

  private Options options;

  /**
   * Instantiates a new CmdGenerator.
   */
  public CmdGenerator() {

  }

  /**
   * Generate options.
   */
  public void generate() {

    this.options = new Options();

    Option numThreads = Option.builder()
        .longOpt("numThreads")
        .hasArg(true)
        .desc("maximum number of threads to run (numThreads - max 1024)")
        .required(true)
        .build();

    Option numSkiers = Option.builder()
        .longOpt("numSkiers")
        .hasArg(true)
        .desc("number of skier to generate lift rides for (numSkiers - max 100000),")
        .required(true)
        .build();

    Option numLifts = Option.builder()
        .longOpt("numLifts")
        .hasArg(true)
        .desc("number of ski lifts (numLifts - range 5-60, default 40)")
        .required(false)
        .build();

    Option numRuns = Option.builder()
        .longOpt("numRuns")
        .hasArg(true)
        .desc("mean numbers of ski lifts each skier rides each day (numRuns - default 10, max 20)")
        .required(false)
        .build();

    Option ip = Option.builder()
        .longOpt("ip")
        .hasArg(true)
        .desc("ip address of server")
        .required(true)
        .build();

    Option port = Option.builder()
        .longOpt("port")
        .hasArg(true)
        .desc("port number of server")
        .required(true)
        .build();

    options.addOption(numThreads);
    options.addOption(numSkiers);
    options.addOption(numLifts);
    options.addOption(numRuns);
    options.addOption(ip);
    options.addOption(port);
  }

  /**
   * Gets options.
   *
   * @return the options
   */
  public Options getOptions() {
    return options;
  }

  /**
   * set options
   *
   * @param options options
   * @throws IllegalArgumentException throws IllegalArgumentException
   */
  public void setOptions(Options options) throws IllegalArgumentException {
    if (options == null) {
      throw new IllegalArgumentException("invalid input");
    }
    this.options = options;
  }

}
