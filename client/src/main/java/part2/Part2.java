package part2;

import part1.Config;
import part1.Part1;

/**
 * The Part2 class is to run Part1 again. In addition, it will write out a Record to a Csv file
 * containing start time, request type, latency, and response code for each request. In the end, it
 * wll print out the mean response time, median response time, throughput, p99, minimum response
 * time, and maximum response time.
 */
public class Part2 {

  private Record record;
  private Part1 part1;
  private Config config;

  /**
   * Instantiates a new Part2.
   *
   * @param part1  the part 1
   * @param record the record
   */
  public Part2(Part1 part1, Record record) {
    this.part1 = part1;
    this.record = record;
    this.config = this.part1.getConfig();
  }

  /**
   * Run long.
   *
   * @return the long
   */
  public long run() {
    long part1WallTime = this.part1.run();
    this.record.setTotalResponseTime(part1WallTime);
    System.out.println("-----------------------------------------------------");
    long startTime = System.currentTimeMillis();
    new CsvWriter().writeCsvRecord(this.config.getParameters(), this.record);
    new Part2Stats(this.record, this.config.getCounter()).printStats();
    long endTime = System.currentTimeMillis();
    long part2WallTime = endTime - startTime;
    System.out.println("-----------------------------------------------------");
    System.out.println("Wall time (part1): " + part1WallTime + " milliseconds");
    System.out.println("Wall time (part2): " + part2WallTime + " milliseconds");
    return part2WallTime;
  }
}
