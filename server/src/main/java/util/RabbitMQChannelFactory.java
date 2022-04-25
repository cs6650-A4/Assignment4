package util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * The RabbitMQChannelFactory is to create a channel pool where channels can be long-lived and
 * re-used.
 */
public class RabbitMQChannelFactory extends BasePooledObjectFactory<Channel> {

//  private final static String HOST = "localhost";
  private final static String HOST = "18.237.94.141";
  private final static int PORT = 5672;

  private ConnectionFactory factory = null;
  private Connection connection = null;

  /**
   * Instantiates a new RabbitMQChannelFactory
   */
  public RabbitMQChannelFactory() {
    factory = new ConnectionFactory();
    factory.setHost(HOST);
    factory.setPort(PORT);
    try {
      connection = factory.newConnection();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Channel create() throws Exception {
    Channel channel = null;
    try {
      channel = connection.createChannel();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return channel;
  }

  @Override
  public PooledObject<Channel> wrap(Channel channel) {
    return new DefaultPooledObject<Channel>(channel);
  }
}
