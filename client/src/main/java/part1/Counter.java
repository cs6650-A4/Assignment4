package part1;

/**
 * The Counter is to record the numbers of successful requests and unsuccessful requests
 */
public class Counter {

  private int successfulReqNum;
  private int unSuccessfulReqNum;

  /**
   * Instantiates a new Counter.
   */
  public Counter() {
    this.successfulReqNum = 0;
    this.unSuccessfulReqNum = 0;
  }

  /**
   * Count successful req.
   */
  public synchronized void countSuccessfulReq() {
    this.successfulReqNum++;
  }

  /**
   * Count unsuccessful req.
   */
  public synchronized void countUnsuccessfulReq() {
    this.unSuccessfulReqNum++;
  }

  /**
   * Gets successful req num.
   *
   * @return the successful req num
   */
  public int getSuccessfulReqNum() {
    return successfulReqNum;
  }

  /**
   * Sets successful req num.
   *
   * @param successfulReqNum the successful req num
   */
  public void setSuccessfulReqNum(int successfulReqNum) {
    this.successfulReqNum = successfulReqNum;
  }

  /**
   * Gets un successful req num.
   *
   * @return the un successful req num
   */
  public int getUnSuccessfulReqNum() {
    return unSuccessfulReqNum;
  }

  /**
   * Sets un successful req num.
   *
   * @param unSuccessfulReqNum the un successful req num
   */
  public void setUnSuccessfulReqNum(int unSuccessfulReqNum) {
    this.unSuccessfulReqNum = unSuccessfulReqNum;
  }
}
