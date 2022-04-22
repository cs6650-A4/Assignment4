package services;

import models.LiftRide;

/**
 * The interface AsynchronousService is to provide asynchronous messaging services
 */
public interface AsynchronousService {

  /**
   * Send a lift ride message to an Asynchronous Queue Channel.
   *
   * @param liftRide the lift ride
   * @return return true if the sending process is successful, otherwise return false
   */
  boolean sendLiftRideToAsynQueueChannel(LiftRide liftRide);

}
