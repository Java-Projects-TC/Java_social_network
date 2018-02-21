package socialnetwork.domain.CoarseDomains;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.BaseDomains.BaseBacklog;
import socialnetwork.domain.BaseDomains.BaseBoard;
import socialnetwork.domain.Task;

public class CoarseBacklog extends BaseBacklog {

  private Lock lock = new ReentrantLock();

  @Override
  public boolean add(Task task) {
    lock.lock();
    try {
      return super.add(task);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public Optional<Task> getNextTaskToProcess() {
    lock.lock();
    try {
      return super.getNextTaskToProcess();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public int numberOfTasksInTheBacklog() {
    return super.numberOfTasksInTheBacklog();
  }
}
