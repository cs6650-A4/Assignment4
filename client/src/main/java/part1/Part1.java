package part1;

/**
 * The Part1 class is to launch 3 phases and print out the statistics.
 */
public class Part1 {

  private Config config;

  /**
   * Instantiates a new Part1
   *
   * @param config the config
   */
  public Part1(Config config) {
    this.config = config;
  }

  /**
   * Run.
   *
   * @return the long
   */
  public long run() {
    long startTime = System.currentTimeMillis();
    PhaseLauncher phaseLauncher = new PhaseLauncher(config);
    phaseLauncher.launchAll();
    phaseLauncher.waitComplete();
    long endTime = System.currentTimeMillis();
    new Part1Stats().printStats(config, startTime, endTime);
    return endTime - startTime;
  }

  /**
   * Gets config.
   *
   * @return the config
   */
  public Config getConfig() {
    return config;
  }

  /**
   * Sets config.
   *
   * @param config the config
   */
  public void setConfig(Config config) {
    this.config = config;
  }
}
