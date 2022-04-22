package part1;

import com.evanlennick.retry4j.CallExecutorBuilder;
import com.evanlennick.retry4j.Status;
import com.evanlennick.retry4j.config.RetryConfig;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import com.evanlennick.retry4j.exception.RetriesExhaustedException;
import com.evanlennick.retry4j.exception.UnexpectedException;
import java.io.IOException;
import java.net.ConnectException;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.concurrent.EventCountCircuitBreaker;
import part1.api.ISkiersApi;

/**
 * The PostSender is to send a skiers api post request to upload new lift ride to server.
 */
public class PostSender implements Runnable {

  private Random random = new Random();
  private Parameters parameters;
  private ISkiersApi skiersApi;
  private CountDownLatch countDownLatch;
  private CountDownLatch complete;
  private int numPost;
  private int startSkierId;
  private int endSkierId;
  private int startTime;
  private int endTime;
  private Counter counter;

  /**
   * Instantiates a PostSender
   *
   * @param parameters     the parameters
   * @param skiersApi      the skiers api
   * @param countDownLatch the countdown latch
   * @param complete       the complete
   * @param numPost        the num post
   * @param startSkierId   the start skier id
   * @param endSkierId     the end skier id
   * @param startTime      the start time
   * @param endTime        the end time
   * @param counter        the counter
   */
  public PostSender(Parameters parameters, ISkiersApi skiersApi, CountDownLatch countDownLatch,
      CountDownLatch complete, int numPost, int startSkierId, int endSkierId, int startTime,
      int endTime, Counter counter) {
    this.parameters = parameters;
    this.skiersApi = skiersApi;
    this.countDownLatch = countDownLatch;
    this.complete = complete;
    this.numPost = numPost;
    this.startSkierId = startSkierId;
    this.endSkierId = endSkierId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.counter = counter;
  }

//  @Override
//  public void run() {
//    Callable<Object> callable = () -> {
//      //code that you want to retry until success OR retries are exhausted OR an unexpected exception is thrown
//      return post();
//    };
//    RetryConfig config = new RetryConfigBuilder()
//        .retryOnSpecificExceptions(ConnectException.class)
//        .retryOnReturnValue(false)
//        .withMaxNumberOfTries(5)
//        .withDelayBetweenTries(30, ChronoUnit.SECONDS)
//        .withExponentialBackoff()
//        .build();
//
//    try {
//      Status<Object> status = new CallExecutorBuilder()
//          .config(config)
//          .build()
//          .execute(callable);
//      Object object = status.getResult(); //the result of the callable logic, if it returns one
//      this.counter.countSuccessfulReq();
//    } catch (RetriesExhaustedException ree) {
//      //the call exhausted all tries without succeeding
//      this.counter.countUnsuccessfulReq();
//      System.out.println("Retry fail....." + ree );
//    } catch (UnexpectedException ue) {
//      //the call threw an unexpected exception
//      System.out.println("Unexpected exceptionl....." + ue );
//      this.counter.countUnsuccessfulReq();
//    }
//
//    if (this.countDownLatch != null) {
//      this.countDownLatch.countDown();
//    }
//    this.complete.countDown();
//  }

  @Override
  public void run() {
    for (int i = 0; i < this.numPost; i++) {
      int retry = 0;
      int statusCode = post();
      int base = 1000;
      int multiplier = 2;
      while (retry < 5 && statusCode != HttpStatus.SC_CREATED) {
        statusCode = post();
        retry++;
        try {
          long wait_interval = (long) (base * Math.pow(multiplier, retry) + random.nextInt(5000));
          Thread.currentThread().sleep(wait_interval);
//          System.out.println("Retry, wait interval: " + wait_interval);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      if (statusCode == HttpStatus.SC_CREATED) {
        this.counter.countSuccessfulReq();
      } else {
        this.counter.countUnsuccessfulReq();
      }
    }
    if (this.countDownLatch != null) {
      this.countDownLatch.countDown();
    }
    this.complete.countDown();
  }

  private int post() {
    int skierID = this.startSkierId + this.random.nextInt(this.endSkierId - this.startSkierId + 1);
    int liftID = this.random.nextInt(this.parameters.getNumLifts() + 1);
    int time = this.startTime + this.random.nextInt(this.endTime - this.startTime + 1);
    int waitTime = Config.WAIT_TIME_START + this.random.nextInt(
        Config.WAIT_TIME_END - Config.WAIT_TIME_START + 1);
    return this.skiersApi.writeNewLiftRide(liftID, time, waitTime, 1000, "2022", "3", skierID);
  }
}
