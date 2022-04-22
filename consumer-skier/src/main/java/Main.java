import static util.Configs.CONSUMER_THREADS_NUM;
import static util.Configs.REDIS_HOST;
import static util.Configs.REDIS_PASSWORD;
import static util.Configs.REDIS_PORT;

import static util.Configs.RMQ_HOST;
import static util.Configs.RMQ_PORT;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import consumer.SkierConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import model.LiftRide;
import redis.clients.jedis.JedisPool;
import repository.IGenericRepository;
import repository.SkierRedisRepository;

public class Main {

  private static SkierConsumer resortConsumer = null;

  public static void main(String[] argv) {
    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(RMQ_HOST);
      factory.setPort(RMQ_PORT);
      Connection connection = factory.newConnection();
      JedisPool jedisPool = new JedisPool(REDIS_HOST, REDIS_PORT, null,REDIS_PASSWORD);
//      JedisPool jedisPool = new JedisPool(REDIS_HOST, REDIS_PORT);
      IGenericRepository<LiftRide> repository = new SkierRedisRepository(jedisPool);
      resortConsumer = new SkierConsumer(connection, repository);
      for (int i = 0; i < CONSUMER_THREADS_NUM; i++) {
        new Thread(new Runnable() {
          @Override
          public void run() {
            resortConsumer.consume();
          }
        }).start();
      }
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }
}
