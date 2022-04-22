package part1;

import part1.api.ISkiersApi;
import part1.cmdLine.IParser;
import part1.exceptions.ParserInitializationException;
import part1.exceptions.SkiersApiInitializationException;

/**
 * The Config class acts as a system's internal configuration center, coordinating with IParser,
 * Parameters, ISkierAPi, Counter. By decoupling classes, it is more flexible for us to choose the
 * different mechanism. For example, we can send api requests by Swagger api or Apache Java HTTP api
 * or something else.
 */
public class Config {

  public static final int PHASE1_START_DAY = 1;
  public static final int PHASE1_END_DAY = 90;
  public static final int PHASE2_START_DAY = 91;
  public static final int PHASE2_END_DAY = 360;
  public static final int PHASE3_START_DAY = 361;
  public static final int PHASE3_END_DAY = 420;
  public static final int WAIT_TIME_START = 0;
  public static final int WAIT_TIME_END = 10;
  public static final String SERVER_NAME = "server";
//public static final String SERVER_NAME = "server_war_exploded";

  private IParser parser;
  private Parameters parameters;
  private ISkiersApi skiersApi;
  private Counter counter;

  /**
   * Instantiates a new Config
   */
  public Config() {
    this.counter = new Counter();
    this.parameters = new Parameters();
  }

  /**
   * Init config.
   *
   * @param args the args
   * @return the config
   * @throws ParserInitializationException    the parser initialization exception
   * @throws SkiersApiInitializationException the skiers api initialization exception
   */
  public Config init(String[] args)
      throws ParserInitializationException, SkiersApiInitializationException {
    if (this.parser == null) {
      throw new ParserInitializationException("Parser initialization fail.");
    }
    if (this.skiersApi == null) {
      throw new SkiersApiInitializationException("Skiers Api initialization fail.");
    }
    this.parser.parse(this.parameters, args);
    this.skiersApi.setBasePath(this.parameters.getIp(), this.parameters.getPort(), SERVER_NAME);
    return this;
  }

  /**
   * Gets parser.
   *
   * @return the parser
   */
  public IParser getParser() {
    return parser;
  }

  /**
   * Sets parser.
   *
   * @param parser the parser
   * @return the parser
   */
  public Config setParser(IParser parser) {
    this.parser = parser;
    return this;
  }

  /**
   * Gets parameters.
   *
   * @return the parameters
   */
  public Parameters getParameters() {
    return parameters;
  }

  /**
   * Sets parameters.
   *
   * @param parameters the parameters
   * @return the parameters
   */
  public Config setParameters(Parameters parameters) {
    this.parameters = parameters;
    return this;
  }

  /**
   * Gets skiers api.
   *
   * @return the skiers api
   */
  public ISkiersApi getSkiersApi() {
    return skiersApi;
  }

  /**
   * Sets skiers api.
   *
   * @param skiersApi the skiers api
   * @return the skiers api
   */
  public Config setSkiersApi(ISkiersApi skiersApi) {
    this.skiersApi = skiersApi;
    return this;
  }

  /**
   * Gets counter.
   *
   * @return the counter
   */
  public Counter getCounter() {
    return counter;
  }

  /**
   * Sets counter.
   *
   * @param counter the counter
   * @return the counter
   */
  public Config setCounter(Counter counter) {
    this.counter = counter;
    return this;
  }

}
