package consumer;

import static util.Configs.EXCHANGE_NAME;
import static util.Configs.SKIER_QUEUE_NAME;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import model.LiftRide;
import repository.IGenericRepository;

public class SkierConsumer {

  private Connection connection;
  private IGenericRepository<LiftRide> repository;

  public SkierConsumer(Connection connection, IGenericRepository repository)
      throws IOException, TimeoutException {
    this.connection = connection;
    this.repository = repository;
  }

  public void consume() {
    Channel channel;
    try {
      channel = this.connection.createChannel();
      channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
      channel.queueDeclare(SKIER_QUEUE_NAME, false, true, false, null);
      channel.queueBind(SKIER_QUEUE_NAME, EXCHANGE_NAME, "");
      channel.basicQos(1);
      DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        //System.out.println(message);
        LiftRide liftRide = new Gson().fromJson(message, LiftRide.class);
//        this.repository.addDataForDaysOnSkierAndSeason(liftRide);
//        this.repository.addDataForLiftsOnSkierAndDay(liftRide);
        this.repository.addDataForVerticals(liftRide);
        this.repository.addDataForTotalDayVerticals(liftRide);
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      };
      channel.basicConsume(SKIER_QUEUE_NAME, false, deliverCallback, consumerTag -> {
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
