package socialnetwork;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.Board;
import socialnetwork.domain.OptimisticDomains.OptimisticBacklog;
import socialnetwork.domain.OptimisticDomains.OptimisticBoard;
import socialnetwork.domain.Worker;

public class Main {

  public static void main(String[] args) {
    // Implement logic here following the steps described in the specs
    // create backlog
    Backlog backlog = new OptimisticBacklog();
    // create social network
    SocialNetwork facebook = new SocialNetwork(backlog);
    // create 5 workers
    Worker[] workers = new Worker[5];
    Arrays.setAll(workers, i -> new Worker(backlog));
    Arrays.stream(workers).forEach(Thread::start);
    // Create 10 users
    User[] users = new User[10];
    Arrays.setAll(users, i -> {
      User user = new User("user" + i, facebook);
      return user;
    });
    // start user threads
    Arrays.stream(users).forEach(u -> {
      Board board = new OptimisticBoard();
      facebook.register(u, board);
      u.start();
    });
    // wait for users to finish
    Arrays.stream(users).forEach(u -> {
      try {
        u.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    // wait for backlog to become empty
    while (backlog.numberOfTasksInTheBacklog() != 0) {
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    // force workers to stop
    Arrays.stream(workers).forEach(w -> w.interrupt());
    Arrays.stream(workers).forEach(w -> {
      try {
        w.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    // wait for workers to terminate
    assertEquals(0, backlog.numberOfTasksInTheBacklog());
    System.out.println("Work's done");
  }
}
