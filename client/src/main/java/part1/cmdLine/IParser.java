package part1.cmdLine;

import part1.Parameters;

/**
 * The interface IParser is to parse command line String[] into Parameters object
 */
public interface IParser {

  /**
   * Parse command line String[] as Parameters object
   *
   * @param parameters the parameters
   * @param args       the args
   * @return the parameters
   */
  Parameters parse(Parameters parameters, String[] args);
}
