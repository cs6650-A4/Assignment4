package part1;

/**
 * The Part1Stats is to calculate the data and print out the statistics.
 */
public class Part1Stats {

  /**
   * Calculate the data and print statistics.
   *
   * @param config    the config
   * @param startTime the start time
   * @param endTime   the end time
   */
  public void printStats(Config config, long startTime, long endTime) {
    System.out.println("Thread: " + config.getParameters().getNumThreads());
    System.out.println("-----------------------------------------------------");
    System.out.println(
        "Number of successful requests sent:   " + config.getCounter().getSuccessfulReqNum());
    System.out.println(
        "Number of unsuccessful requests sent: " + config.getCounter().getUnSuccessfulReqNum());
    System.out.println("Wall time: " + (endTime - startTime) + " milliseconds");
    double throughput =
        (double) (config.getCounter().getSuccessfulReqNum() + config.getCounter().getUnSuccessfulReqNum()) /
            ((double) (endTime - startTime) / 1000);
    System.out.println(
        "Throughput: " + String.format("%.2f", throughput) + " requests/seconds");
  }
}
