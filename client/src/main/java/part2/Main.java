package part2;

import io.swagger.client.api.SkiersApi;
import part1.Part1;
import part1.api.SwaggerSkiersApi;
import part1.cmdLine.CmdGenerator;
import part1.cmdLine.CmdIParser;
import part1.Config;
import part1.exceptions.ParserInitializationException;
import part1.exceptions.SkiersApiInitializationException;

/**
 * The Main class is the entry point to run Part2 by specifying the configuration of the Parser and
 * SkiersApi. In Part2, we used Proxy Design Pattern to provide an Object ProxySkierApi that acts as
 * a substitute for SkiersApi, adding some additional behaviors, such as calculating the latency of
 * each post request.
 */
public class Main {

  // --numThreads 32 --numSkiers 20000 --numLifts 40 --numRuns 20 --ip localhost --port 8080
  // --numThreads 128 --numSkiers 20000 --numLifts 40 --numRuns 20 --ip 52.24.40.21 --port 8080
  // --numThreads 128 --numSkiers 20000 --numLifts 40 --numRuns 20 --ip cs6650-ALB-901835977.us-west-2.elb.amazonaws.com --port 8080

  public static void main(String[] args) {
    Record record = new Record();
    try {
      Config config = new Config()
          .setParser(new CmdIParser(new CmdGenerator()))
          .setSkiersApi(new ProxySkierApi(new SwaggerSkiersApi(new SkiersApi()), record))
          .init(args);
      new Part2(new Part1(config), record).run();
    } catch (ParserInitializationException | SkiersApiInitializationException e) {
      e.printStackTrace();
    }
  }
}
