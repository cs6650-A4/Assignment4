import consumer.SkierConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import repository.IGenericRepository;
import repository.LiftRideRedisRepository;

public class Main {

  private final static int THREADS_NUM = 5;
  private static SkierConsumer skierConsumer = null;

  public static void main(String[] argv) {
    try {
      skierConsumer = new SkierConsumer();
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < THREADS_NUM; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          skierConsumer.consume();
        }
      }).start();
    }
  }
}
