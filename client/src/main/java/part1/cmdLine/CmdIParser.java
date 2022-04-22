package part1.cmdLine;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import part1.Parameters;

/**
 * The CmdParser class that implements IParser interface is to parse the external command line
 * parameters of numTreads, numSkiers, numLifts, numRuns and ip/port as Parameter Object. It also
 * acts as a validator to check the correctness of the input command line arguments. For example,
 * the maximum number of threads should not exceed 1024, and the IPv4 address should be referred to
 * as dotted-decimal format or "localhost".
 */
public class CmdIParser implements IParser {

  private final static String NUM_THREADS = "numThreads";
  private final static String NUM_SKIERS = "numSkiers";
  private final static String NUM_LIFTS = "numLifts";
  private final static String NUM_RUNS = "numRuns";
  private final static String IP = "ip";
  private final static String PORT = "port";
  private final static String LOCALHOST = "localhost";

  private final static int MIN_NUM_THREADS = 1;
  private final static int MAX_NUM_THREADS = 1024;
  private final static int MIN_NUM_SKIERS = 1;
  private final static int MAX_NUM_SKIERS = 100000;
  private final static int MIN_NUM_LIFTS = 5;
  private final static int MAX_NUM_LIFTS = 60;
  private final static int DEFAULT_NUM_LIFTS = 40;
  private final static int MIN_NUM_RUNS = 1;
  private final static int MAX_NUM_RUNS = 20;
  private final static int DEFAULT_NUM_RUNS = 10;

  private final static int SYSTEM_EXIT_NUM = 0;

  private CmdGenerator cmdGenerator;
  private HelpFormatter formatter;

  /**
   * Instantiates a new CmdParser.
   *
   * @param cmdGenerator the cmdGenerator
   */
  public CmdIParser(CmdGenerator cmdGenerator) {
    this.cmdGenerator = cmdGenerator;
    this.formatter = new HelpFormatter();
  }

  @Override
  public Parameters parse(Parameters parameters, String[] args) {
    cmdGenerator.generate();
    CommandLine cmd = null;
    try {
      cmd = new DefaultParser().parse(cmdGenerator.getOptions(), args);
      if (parseNumThreads(parameters, cmd) &&
          parseNumSkiers(parameters, cmd) &&
          parseNumLifts(parameters, cmd) &&
          parseNumRuns(parameters, cmd) &&
          parseIp(parameters, cmd) &&
          parsePort(parameters, cmd)) {
        handleParsingSuccess();
      } else {
        handleParsingError();
      }
    } catch (ParseException e) {
      handleParsingError();
    }
    return parameters;
  }

  /**
   * Gets cmd generator.
   *
   * @return the cmd generator
   */
  public CmdGenerator getCmdGenerator() {
    return cmdGenerator;
  }

  /**
   * Sets cmd generator.
   *
   * @param cmdGenerator the cmd generator
   */
  public void setCmdGenerator(CmdGenerator cmdGenerator) {
    this.cmdGenerator = cmdGenerator;
  }

  private void handleParsingSuccess() {
    System.out.println("Complete reading parameters from command line...");
  }

  private void handleParsingError() {
    this.formatter.printHelp(" ", " ", cmdGenerator.getOptions(), "", false);
    System.exit(SYSTEM_EXIT_NUM);
  }

  private boolean parseNumThreads(Parameters parameters, CommandLine cmd)
      throws NumberFormatException {
    int numTreads = Integer.parseInt(cmd.getOptionValue(NUM_THREADS));
    if (numTreads > MAX_NUM_THREADS || numTreads < MIN_NUM_THREADS) {
      return false;
    }
    parameters.setNumThreads(numTreads);
    return true;
  }

  private boolean parseNumSkiers(Parameters parameters, CommandLine cmd)
      throws NumberFormatException {
    int numSkiers = Integer.parseInt(cmd.getOptionValue(NUM_SKIERS));
    if (numSkiers > MAX_NUM_SKIERS || numSkiers < MIN_NUM_SKIERS) {
      return false;
    }
    parameters.setNumSkiers(numSkiers);
    return true;
  }

  private boolean parseNumLifts(Parameters parameters, CommandLine cmd)
      throws NumberFormatException {
    if (cmd.getOptionValue(NUM_LIFTS) == null) {
      parameters.setNumLifts(DEFAULT_NUM_LIFTS);
      return true;
    }
    int numLifts = Integer.parseInt(cmd.getOptionValue(NUM_LIFTS));
    if (numLifts > MAX_NUM_LIFTS || numLifts < MIN_NUM_LIFTS) {
      return false;
    }
    parameters.setNumLifts(numLifts);
    return true;
  }

  private boolean parseNumRuns(Parameters parameters, CommandLine cmd)
      throws NumberFormatException {
    if (cmd.getOptionValue(NUM_RUNS) == null) {
      parameters.setNumRuns(DEFAULT_NUM_RUNS);
      return true;
    }
    int numRuns = Integer.parseInt(cmd.getOptionValue(NUM_RUNS));
    if (numRuns > MAX_NUM_RUNS || numRuns < MIN_NUM_RUNS) {
      return false;
    }
    parameters.setNumRuns(numRuns);
    return true;
  }

  private boolean parseIp(Parameters parameters, CommandLine cmd) {
    String ipAddress = cmd.getOptionValue(IP);
    if (ipAddress == null) {
      return false;
    }
    if (ipAddress.equals(LOCALHOST)) {
      parameters.setIp(ipAddress);
      return true;
    }
    String unit = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
    String regex = unit + "\\." + unit + "\\." + unit + "\\." + unit;
    if (ipAddress.matches(regex)) {
      parameters.setIp(cmd.getOptionValue(IP));
      return true;
    }
    parameters.setIp(ipAddress);
    return true;
  }

  private boolean parsePort(Parameters parameters, CommandLine cmd) {
    String portNum = cmd.getOptionValue(PORT);
    if (portNum == null) {
      return false;
    }
    String regex = "[0-9]+";
    if (portNum.matches(regex)) {
      parameters.setPort(cmd.getOptionValue(PORT));
      return true;
    }
    return false;
  }

}
