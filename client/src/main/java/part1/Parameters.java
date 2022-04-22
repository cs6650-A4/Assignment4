package part1;

/**
 * The Parameters contain the information of numThreads, numSkiers, numLifts, numRuns, ip and port.
 */
public class Parameters {

  private int numThreads;
  private int numSkiers;
  private int numLifts;
  private int numRuns;
  private String ip;
  private String port;

  /**
   * Instantiates a new Parameters.
   */
  public Parameters() {
  }

  /**
   * Instantiates a new Parameters.
   *
   * @param numThreads the num threads
   * @param numSkiers  the num skiers
   * @param numLifts   the num lifts
   * @param numRuns    the num runs
   * @param ip         the ip
   * @param port       the port
   */
  public Parameters(int numThreads, int numSkiers, int numLifts, int numRuns, String ip,
      String port) {
    this.numThreads = numThreads;
    this.numSkiers = numSkiers;
    this.numLifts = numLifts;
    this.numRuns = numRuns;
    this.ip = ip;
    this.port = port;
  }

  /**
   * Gets num threads.
   *
   * @return the num threads
   */
  public int getNumThreads() {
    return numThreads;
  }

  /**
   * Sets num threads.
   *
   * @param numThreads the num threads
   */
  public void setNumThreads(int numThreads) {
    this.numThreads = numThreads;
  }

  /**
   * Gets num skiers.
   *
   * @return the num skiers
   */
  public int getNumSkiers() {
    return numSkiers;
  }

  /**
   * Sets num skiers.
   *
   * @param numSkiers the num skiers
   */
  public void setNumSkiers(int numSkiers) {
    this.numSkiers = numSkiers;
  }

  /**
   * Gets num lifts.
   *
   * @return the num lifts
   */
  public int getNumLifts() {
    return numLifts;
  }

  /**
   * Sets num lifts.
   *
   * @param numLifts the num lifts
   */
  public void setNumLifts(int numLifts) {
    this.numLifts = numLifts;
  }

  /**
   * Gets num runs.
   *
   * @return the num runs
   */
  public int getNumRuns() {
    return numRuns;
  }

  /**
   * Sets num runs.
   *
   * @param numRuns the num runs
   */
  public void setNumRuns(int numRuns) {
    this.numRuns = numRuns;
  }

  /**
   * Gets ip.
   *
   * @return the ip
   */
  public String getIp() {
    return ip;
  }

  /**
   * Sets ip.
   *
   * @param ip the ip
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * Gets port.
   *
   * @return the port
   */
  public String getPort() {
    return port;
  }

  /**
   * Sets port.
   *
   * @param port the port
   */
  public void setPort(String port) {
    this.port = port;
  }
}
