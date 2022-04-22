package consumer;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;
import model.LiftRide;
import repository.IGenericRepository;
import repository.LiftRideRedisRepository;

public class SkierConsumer {

  private final static String QUEUE_NAME = "skier_lift_ride_msg";
  private final static String RMQ_HOST = "localhost";
  //private final static String RMQ_HOST = "34.209.42.249";
  private final static int RMQ_PORT = 5672;

  private ConnectionFactory factory;
  private Connection connection;
  private IGenericRepository repository;

  public SkierConsumer() throws IOException, TimeoutException {
    this.factory = new ConnectionFactory();
    this.factory.setHost(RMQ_HOST);
    this.factory.setPort(RMQ_PORT);
    this.connection = factory.newConnection();
    this.repository = new LiftRideRedisRepository();
  }

  public void consume() {
    Channel channel;
    try {
      channel = this.connection.createChannel();
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      channel.basicQos(1);
      DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(message);
        LiftRide liftRide = new Gson().fromJson(message, LiftRide.class);
        repository.add(liftRide);
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      };
      channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
      });

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
