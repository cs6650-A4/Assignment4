package services;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import java.util.concurrent.TimeUnit;
import models.LiftRide;
import org.apache.commons.lang3.concurrent.EventCountCircuitBreaker;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * The RabbitMqAsynchronousServiceImpl implements the AsynchronousService interface by using
 * RabbitMQ technique
 */
public class RabbitMqAsynchronousServiceImpl implements AsynchronousService {

  private final static String EXCHANGE_NAME = "ski_exchange";
  private GenericObjectPool<Channel> channelPool;

  /**
   * Instantiates a new Rabbit mq asynchronous service.
   *
   * @param channelPool the channel pool
   */
  public RabbitMqAsynchronousServiceImpl(
      GenericObjectPool<Channel> channelPool) {
    this.channelPool = channelPool;
  }

  public boolean sendLiftRideToAsynQueueChannel(LiftRide liftRide) {
    Channel channel = null;
    try {
      channel = this.channelPool.borrowObject();
      channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
      String message = new Gson().toJson(liftRide);
      channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      this.channelPool.returnObject(channel);
    }
    return true;
  }
}
