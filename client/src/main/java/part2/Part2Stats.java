package part2;

import java.util.Collections;
import part1.Counter;

/**
 * The Part2Stats is to calculate the data from part2 and print out the statistics.
 */
public class Part2Stats {

  private Record record;
  private Counter counter;

  /**
   * Instantiates a new Part 2 stats.
   *
   * @param record  the record
   * @param counter the counter
   */
  public Part2Stats(Record record, Counter counter) {
    this.record = record;
    this.counter = counter;
    Collections.sort(this.record.getLatencyList());
  }


  /**
   * Print stats.
   */
  public void printStats() {
    System.out.println("Mean response time: " + calculateMeanResponseTime() + " milliseconds");
    System.out.println("Median response time: " + calculateMedianResponseTime() + " milliseconds");
    System.out.println(
        "Throughput: " + String.format("%.2f", calculateThroughput()) + " requests/seconds");
    System.out.println("99th percentile response time: " + calculateP99() + " milliseconds");
    System.out.println("Minimum Response time: " + calculateMinResponseTime() + " milliseconds");
    System.out.println("Maximum Response time: " + calculateMaxResponseTime() + " milliseconds");
  }

  private long calculateMeanResponseTime() {
    long totalResponseTime = 0;
    for (long latency : this.record.getLatencyList()) {
      totalResponseTime += latency;
    }
    return totalResponseTime / this.record.getLatencyList().size();
  }

  private long calculateMedianResponseTime() {
    int size = this.record.getLatencyList().size();
    if (size % 2 == 0) {
      long m1 = this.record.getLatencyList().get((size / 2) - 1);
      long m2 = this.record.getLatencyList().get(size / 2);
      return (m1 + m2) / 2;
    }
    return this.record.getLatencyList().get(size / 2);
  }

  private double calculateThroughput() {
    return (double) (counter.getSuccessfulReqNum() + counter.getUnSuccessfulReqNum())
        / ((double) record.getTotalResponseTime() / 1000);
  }

  private long calculateP99() {
    int size = this.record.getLatencyList().size();
    int index = (int) (size * 0.99);
    return this.record.getLatencyList().get(index);
  }

  private long calculateMinResponseTime() {
    return this.record.getLatencyList().get(0);
  }

  private long calculateMaxResponseTime() {
    return this.record.getLatencyList().get(this.record.getLatencyList().size() - 1);
  }


}
