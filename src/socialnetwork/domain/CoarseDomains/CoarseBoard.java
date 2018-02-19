package socialnetwork.domain.CoarseDomains;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import socialnetwork.domain.BaseDomains.BaseBoard;
import socialnetwork.domain.Message;

public class CoarseBoard extends BaseBoard{

  private Lock lock = new ReentrantLock();

  @Override
  public boolean addMessage(Message message) {
    lock.lock();
    try {
      return super.add(message);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean deleteMessage(Message message) {
    lock.lock();
    try {
      return super.delete(message);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public int size() {
    return super.getSize();
  }

  @Override
  public List<Message> getBoardSnapshot() {
    return null;
  }
}
